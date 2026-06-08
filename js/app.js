(function () {
  'use strict';

  const items = [];

  const video = document.getElementById('preview');
  const btnStart = document.getElementById('btnStart');
  const btnStop = document.getElementById('btnStop');
  const scanStatus = document.getElementById('scanStatus');
  const manualForm = document.getElementById('manualForm');
  const manualInput = document.getElementById('manualInput');
  const resultBody = document.getElementById('resultBody');
  const resultTable = document.getElementById('resultTable');
  const emptyMessage = document.getElementById('emptyMessage');
  const listCount = document.getElementById('listCount');
  const btnClear = document.getElementById('btnClear');
  const btnPdf = document.getElementById('btnPdf');

  const codeReader = new ZXing.BrowserMultiFormatReader();
  let scanning = false;

  function setStatus(message, type) {
    scanStatus.textContent = message || '';
    scanStatus.className = 'status' + (type ? ' ' + type : '');
  }

  function formatTime(date) {
    return date.toLocaleString('pt-BR');
  }

  function renderList() {
    resultBody.innerHTML = '';
    items.forEach((item, index) => {
      const row = document.createElement('tr');
      row.innerHTML =
        '<td>' + (index + 1) + '</td>' +
        '<td>' + escapeHtml(item.code) + '</td>' +
        '<td>' + (item.source === 'camera' ? 'Câmera' : 'Manual') + '</td>' +
        '<td>' + formatTime(item.time) + '</td>' +
        '<td><button class="removeBtn" type="button" data-index="' + index + '">remover</button></td>';
      resultBody.appendChild(row);
    });

    listCount.textContent = items.length;
    const hasItems = items.length > 0;
    resultTable.classList.toggle('hidden', !hasItems);
    emptyMessage.classList.toggle('hidden', hasItems);
  }

  function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
  }

  function addItem(code, source) {
    const trimmed = code.trim();
    if (!trimmed) {
      return false;
    }
    if (items.some((item) => item.code === trimmed)) {
      setStatus('Patrimônio "' + trimmed + '" já está na lista.', 'error');
      return false;
    }
    items.push({ code: trimmed, source: source, time: new Date() });
    renderList();
    return true;
  }

  resultBody.addEventListener('click', (event) => {
    const target = event.target;
    if (target.classList.contains('removeBtn')) {
      const index = Number(target.dataset.index);
      items.splice(index, 1);
      renderList();
    }
  });

  btnClear.addEventListener('click', () => {
    if (items.length === 0) {
      return;
    }
    if (window.confirm('Limpar toda a lista de patrimônios lidos?')) {
      items.length = 0;
      renderList();
      setStatus('Lista limpa.', '');
    }
  });

  manualForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const code = manualInput.value;
    if (addItem(code, 'manual')) {
      setStatus('Patrimônio "' + code.trim() + '" adicionado manualmente.', 'success');
      manualInput.value = '';
    }
    manualInput.focus();
  });

  async function startScanning() {
    setStatus('Solicitando acesso à câmera...', '');
    btnStart.disabled = true;
    try {
      const devices = await ZXing.BrowserMultiFormatReader.listVideoInputDevices();
      const backCamera = devices.find((device) => /back|traseira|rear|environment/i.test(device.label));
      const deviceId = backCamera ? backCamera.deviceId : (devices[0] && devices[0].deviceId);

      await codeReader.decodeFromVideoDevice(deviceId, video, (result, error) => {
        if (result) {
          const code = result.getText();
          if (addItem(code, 'camera')) {
            setStatus('Patrimônio "' + code + '" capturado pela câmera.', 'success');
          }
        }
      });

      scanning = true;
      btnStop.disabled = false;
      setStatus('Câmera ligada. Aponte para o código de barras da etiqueta.', '');
    } catch (err) {
      btnStart.disabled = false;
      setStatus('Não foi possível acessar a câmera. Use a opção de digitar manualmente abaixo.', 'error');
      console.error(err);
    }
  }

  function stopScanning() {
    if (!scanning) {
      return;
    }
    codeReader.reset();
    scanning = false;
    btnStart.disabled = false;
    btnStop.disabled = true;
    setStatus('Câmera desligada.', '');
  }

  btnStart.addEventListener('click', startScanning);
  btnStop.addEventListener('click', stopScanning);

  btnPdf.addEventListener('click', () => {
    if (items.length === 0) {
      setStatus('Adicione ao menos um patrimônio antes de salvar o PDF.', 'error');
      return;
    }

    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    const pageWidth = doc.internal.pageSize.getWidth();
    const marginLeft = 14;
    let y = 18;

    doc.setFontSize(16);
    doc.text('Relatório de Patrimônios Lidos', marginLeft, y);

    doc.setFontSize(10);
    y += 8;
    doc.text('Gerado em: ' + formatTime(new Date()), marginLeft, y);
    doc.text('Total de itens: ' + items.length, pageWidth - marginLeft, y, { align: 'right' });

    y += 10;
    doc.setFontSize(11);
    doc.setFont(undefined, 'bold');
    doc.text('#', marginLeft, y);
    doc.text('Patrimônio', marginLeft + 14, y);
    doc.text('Origem', marginLeft + 90, y);
    doc.text('Horário', marginLeft + 130, y);
    doc.setFont(undefined, 'normal');
    y += 2;
    doc.line(marginLeft, y, pageWidth - marginLeft, y);

    items.forEach((item, index) => {
      y += 8;
      if (y > doc.internal.pageSize.getHeight() - 16) {
        doc.addPage();
        y = 18;
      }
      doc.text(String(index + 1), marginLeft, y);
      doc.text(item.code, marginLeft + 14, y);
      doc.text(item.source === 'camera' ? 'Câmera' : 'Manual', marginLeft + 90, y);
      doc.text(formatTime(item.time), marginLeft + 130, y);
    });

    const fileName = 'patrimonios-' + new Date().toISOString().slice(0, 19).replace(/[:T]/g, '-') + '.pdf';
    doc.save(fileName);
    setStatus('PDF salvo: ' + fileName, 'success');
  });

  renderList();
})();
