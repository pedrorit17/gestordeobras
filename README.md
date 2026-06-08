# Leitor de Patrimônio

Site web simples para ler o número impresso nas etiquetas de patrimônio (ex: `001321364-4`) usando a câmera do dispositivo e reconhecimento de texto (OCR), com opção de digitação manual quando a leitura falhar. Os itens lidos ficam armazenados na sessão atual e podem ser exportados para PDF ao final.

## Como usar

Abra o `index.html` em um navegador (em um celular, de preferência via HTTPS, pois o acesso à câmera exige isso fora de `localhost`):

1. Clique em **Ligar câmera**, posicione o número do patrimônio dentro do quadro vermelho e toque em **Capturar e ler número**.
2. Confira o número reconhecido (e corrija se necessário) antes de tocar em **Adicionar à lista**.
3. Caso a leitura falhe, digite o número do patrimônio no campo de **Adicionar manualmente**.
4. Acompanhe a lista de patrimônios lidos na sessão.
5. Ao final, clique em **Salvar PDF** para baixar um relatório com todos os itens lidos.

## Tecnologias

- HTML, CSS e JavaScript puro
- [Tesseract.js](https://github.com/naptha/tesseract.js) para reconhecimento de texto (OCR) do número do patrimônio pela câmera
- [jsPDF](https://github.com/parallax/jsPDF) para geração do relatório em PDF
