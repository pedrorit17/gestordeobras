# Leitor de Patrimônio

Site web simples para ler etiquetas de patrimônio (código de barras) usando a câmera do dispositivo, com opção de digitação manual quando a leitura falhar. Os itens lidos ficam armazenados na sessão atual e podem ser exportados para PDF ao final.

## Como usar

Abra o `index.html` em um navegador (em um celular, de preferência via HTTPS, pois o acesso à câmera exige isso fora de `localhost`):

1. Clique em **Ligar câmera** e aponte para o código de barras da etiqueta de patrimônio.
2. Caso a leitura falhe, digite o número do patrimônio no campo de **Adicionar manualmente**.
3. Acompanhe a lista de patrimônios lidos na sessão.
4. Ao final, clique em **Salvar PDF** para baixar um relatório com todos os itens lidos.

## Tecnologias

- HTML, CSS e JavaScript puro
- [ZXing](https://github.com/zxing-js/library) para leitura de código de barras pela câmera
- [jsPDF](https://github.com/parallax/jsPDF) para geração do relatório em PDF
