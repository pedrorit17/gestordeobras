(function () {
  'use strict';

  const items = [];

  const video = document.getElementById('preview');
  const canvas = document.getElementById('captureCanvas');
  const btnStart = document.getElementById('btnStart');
  const btnCapture = document.getElementById('btnCapture');
  const btnStop = document.getElementById('btnStop');
  const scanStatus = document.getElementById('scanStatus');
  const reviewPanel = document.getElementById('reviewPanel');
  const reviewInput = document.getElementById('reviewInput');
  const btnReviewAdd = document.getElementById('btnReviewAdd');
  const btnReviewDiscard = document.getElementById('btnReviewDiscard');
  const manualForm = document.getElementById('manualForm');
  const manualInput = document.getElementById('manualInput');
  const resultBody = document.getElementById('resultBody');
  const resultTable = document.getElementById('resultTable');
  const emptyMessage = document.getElementById('emptyMessage');
  const listCount = document.getElementById('listCount');
  const btnClear = document.getElementById('btnClear');
  const btnPdf = document.getElementById('btnPdf');

  let mediaStream = null;
  let ocrWorker = null;

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
        '<td>' + (item.source === 'camera' ? 'Câmera (OCR)' : 'Manual') + '</td>' +
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

    const constraintsOptions = [
      { video: { facingMode: { ideal: 'environment' } } },
      { video: true }
    ];

    let lastError = null;
    for (const constraints of constraintsOptions) {
      try {
        // Pede a câmera diretamente: em navegadores móveis (sobretudo iOS Safari)
        // o prompt de permissão só aparece de forma confiável dentro de um
        // getUserMedia disparado pelo gesto do usuário (não ao listar dispositivos antes).
        mediaStream = await navigator.mediaDevices.getUserMedia(constraints);
        video.srcObject = mediaStream;
        await video.play();

        btnStop.disabled = false;
        btnCapture.disabled = false;
        setStatus('Câmera ligada. Centralize o número do patrimônio no quadro e toque em "Capturar e ler número".', '');
        return;
      } catch (err) {
        console.warn('Falha ao iniciar câmera com', constraints, err);
        lastError = err;
      }
    }

    btnStart.disabled = false;
    reportCameraError(lastError);
  }

  function reportCameraError(err) {
    if (err && (err.name === 'NotAllowedError' || err.name === 'PermissionDeniedError')) {
      setStatus('Permissão da câmera negada. Habilite o acesso à câmera para este site nas configurações do navegador e tente novamente.', 'error');
    } else if (location.protocol !== 'https:' && location.hostname !== 'localhost') {
      setStatus('O acesso à câmera só funciona em conexões seguras (HTTPS). Abra o site pelo link https.', 'error');
    } else {
      setStatus('Não foi possível acessar a câmera. Use a opção de digitar manualmente abaixo.', 'error');
    }
    if (err) {
      console.error(err);
    }
  }

  function stopScanning() {
    if (mediaStream) {
      mediaStream.getTracks().forEach((track) => track.stop());
      mediaStream = null;
    }
    video.srcObject = null;
    btnStart.disabled = false;
    btnStop.disabled = true;
    btnCapture.disabled = true;
    hideReviewPanel();
    setStatus('Câmera desligada.', '');
  }

  btnStart.addEventListener('click', startScanning);
  btnStop.addEventListener('click', stopScanning);

  async function getOcrWorker() {
    if (!ocrWorker) {
      const { createWorker } = Tesseract;
      ocrWorker = await createWorker('eng');
      await ocrWorker.setParameters({
        tessedit_char_whitelist: '0123456789-./',
        tessedit_pageseg_mode: '7'
      });
    }
    return ocrWorker;
  }

  function cleanRecognizedText(text) {
    return text
      .replace(/[^0-9\-./]/g, '')
      .replace(/\s+/g, '')
      .trim();
  }

  function showReviewPanel(recognizedText) {
    reviewInput.value = recognizedText;
    reviewPanel.classList.remove('hidden');
    reviewInput.focus();
    reviewInput.select();
  }

  function hideReviewPanel() {
    reviewPanel.classList.add('hidden');
    reviewInput.value = '';
  }

  btnCapture.addEventListener('click', async () => {
    if (!mediaStream) {
      return;
    }
    btnCapture.disabled = true;
    setStatus('Lendo número do patrimônio...', '');
    hideReviewPanel();

    try {
      const videoWidth = video.videoWidth;
      const videoHeight = video.videoHeight;

      // Recorta a região central do quadro (onde o usuário deve posicionar o
      // número), pois isso melhora bastante a precisão do OCR.
      const cropWidth = videoWidth * 0.76;
      const cropHeight = videoHeight * 0.24;
      const cropX = (videoWidth - cropWidth) / 2;
      const cropY = (videoHeight - cropHeight) / 2;

      canvas.width = cropWidth;
      canvas.height = cropHeight;
      const ctx = canvas.getContext('2d');
      ctx.drawImage(video, cropX, cropY, cropWidth, cropHeight, 0, 0, cropWidth, cropHeight);

      const worker = await getOcrWorker();
      const { data } = await worker.recognize(canvas);
      const recognized = cleanRecognizedText(data.text || '');

      if (recognized) {
        setStatus('Número reconhecido. Confira antes de adicionar.', 'success');
        showReviewPanel(recognized);
      } else {
        setStatus('Não foi possível reconhecer o número. Tente novamente ou digite manualmente.', 'error');
      }
    } catch (err) {
      setStatus('Erro ao processar a imagem. Tente novamente ou digite manualmente.', 'error');
      console.error(err);
    } finally {
      btnCapture.disabled = false;
    }
  });

  btnReviewAdd.addEventListener('click', () => {
    const code = reviewInput.value;
    if (addItem(code, 'camera')) {
      setStatus('Patrimônio "' + code.trim() + '" adicionado.', 'success');
      hideReviewPanel();
    }
  });

  btnReviewDiscard.addEventListener('click', () => {
    hideReviewPanel();
    setStatus('Leitura descartada. Aponte a câmera novamente e capture.', '');
  });

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
      doc.text(item.source === 'camera' ? 'Câmera (OCR)' : 'Manual', marginLeft + 90, y);
      doc.text(formatTime(item.time), marginLeft + 130, y);
    });

    const fileName = 'patrimonios-' + new Date().toISOString().slice(0, 19).replace(/[:T]/g, '-') + '.pdf';
    doc.save(fileName);
    setStatus('PDF salvo: ' + fileName, 'success');
  });

  window.addEventListener('beforeunload', () => {
    if (ocrWorker) {
      ocrWorker.terminate();
    }
    if (mediaStream) {
      mediaStream.getTracks().forEach((track) => track.stop());
    }
  });

  renderList();
})();
