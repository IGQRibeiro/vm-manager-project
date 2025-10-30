# ⚡ VM Manager UI (Angular)

Interface web para gerenciamento de máquinas virtuais, desenvolvida com
**Angular**.

------------------------------------------------------------------------

## ✅ Requisitos

Tecnologia    Versão
  ------------- -------------------------
Node          18+
npm           Incluído com Node
Angular CLI   `npm i -g @angular/cli`

------------------------------------------------------------------------

## ▶️ Desenvolvimento (localhost)

``` bash
npm install
ng serve
```

Acesse em: **http://localhost:4200**

> ⚠️ A API deve estar rodando em **http://localhost:8080**

------------------------------------------------------------------------

## 🔌 Integração com a API

URL padrão da API:

``` bash
http://localhost:8080/api/v1
```

Altere se necessário no arquivo:

    src/app/core/services/vm.service.ts

------------------------------------------------------------------------

## 🧭 Rotas Principais

Rota      Descrição
  --------- ----------------------------------------------
`/vms`    CRUD de VMs + Start/Stop/Suspend\
`/logs`   Lista paginada de logs (ação, VM, data/hora)

------------------------------------------------------------------------

## 📦 Build de produção

``` bash
ng build
```

Artefatos gerados em:

    dist/vm-manager-ui/

### Servir build localmente (opcional)

``` bash
npx serve ./dist/vm-manager-ui -l 4200
```

Aplicação disponível em **http://localhost:4200**

------------------------------------------------------------------------

## 🗂️ Estrutura do Projeto

    src/
     ├─ app/
     │  ├─ core/
     │  │  └─ services/vm.service.ts
     │  └─ pages/
     │     ├─ vm-list/
     │     └─ vm-logs/
     ├─ app.routes.ts
     ├─ main.ts
     └─ styles.scss
