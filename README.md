# 📱 Portfólio Mobile - Android (Java)
Este é o projeto do meu portfólio mobile, desenvolvido em Java para Android. O app apresenta informações gerais sobre mim, além de listar meus repositórios públicos do GitHub de forma personalizada, consumindo uma API própria criada em Node.js.
> 💡 Também existe uma versão web deste portfólio: [GitHub](https://github.com/SoaresCRF/web-portfolio "Projeto no GitHub") | [Site](https://soarescrf.github.io/web-portfolio/ "Versão web")

## ✨ Funcionalidades principais
- Listagem dinâmica dos meus repositórios públicos do GitHub.
- Pesquisa dos repositórios por nome, tipo de tecnologia e ordenação (por data ↑↓ / A-Z).
- Design simples, leve e funcional.
- Consumo de dados via API Node.js (hospedada no Render).

## 🛠️ Tecnologias utilizadas
- Android nativo
- Java
- Consumo de API REST
- Node.js *(somente como back-end intermediário para requisições GitHub, não incluído neste repositório)*

## 🔌 Como funciona a arquitetura
```plaintext
Android App
    ↓
API Node.js (Render)
    ↓
GitHub API
```
- *O app não se conecta diretamente à API pública do GitHub, todas as requisições passam primeiro pela minha API backend (Node.js) hospedada no Render.*

## 📦 Download App
Baixe a versão mais recente do aplicativo para testar diretamente no seu dispositivo Android: [APK](https://github.com/SoaresCRF/java-mobile-portfolio/releases/download/v1.0.0/soares-v1.0.0.apk "Download do APK") | [PlayStore](# "Ver na PlayStore")

## 📌 Melhorias futuras
- Publicação futura na Google Play Store.

## 📄 Licença
Este projeto está sob a licença MIT.