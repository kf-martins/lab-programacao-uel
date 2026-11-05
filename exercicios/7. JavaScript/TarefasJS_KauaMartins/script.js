let tarefas = [];
let proximoId = 1;
let filtroAtual = "todas";

const inputTarefa = document.getElementById("inputTarefa");
const botaoAdicionar = document.getElementById("botaoAdicionar");
const formTarefa = document.getElementById("formTarefa");
const filtroTodas = document.getElementById("filtroTodas");
const filtroAtivas = document.getElementById("filtroAtivas");
const filtroConcluidas = document.getElementById("filtroConcluidas");
const contador = document.getElementById("contador");
const listaTarefas = document.getElementById("listaTarefas");

function adicionarTarefa() {
  const descricao = inputTarefa.value.trim();

  if (descricao === "") {
    inputTarefa.focus();
    inputTarefa.setAttribute("aria-invalid", "true");
    inputTarefa.placeholder = "Digite uma descrição antes de adicionar";
    return;
  }

  inputTarefa.removeAttribute("aria-invalid");
  inputTarefa.placeholder = "O que você precisa fazer?";

  tarefas.push({
    id: proximoId,
    descricao: descricao,
    concluida: false
  });

  proximoId++;
  inputTarefa.value = "";
  inputTarefa.focus();
  renderizar();
}

function alternarTarefa(id) {
  tarefas = tarefas.map(tarefa =>
    tarefa.id === id
      ? { ...tarefa, concluida: !tarefa.concluida }
      : tarefa
  );

  renderizar();
}

function removerTarefa(id) {
  const item = listaTarefas.querySelector(`[data-id="${id}"]`);

  if (item) {
    item.classList.add("removing");
    item.addEventListener("animationend", () => {
      tarefas = tarefas.filter(tarefa => tarefa.id !== id);
      renderizar();
    }, { once: true });
  } else {
    tarefas = tarefas.filter(tarefa => tarefa.id !== id);
    renderizar();
  }
}

function renderizar() {
  listaTarefas.innerHTML = "";

  const tarefasFiltradas = tarefas.filter(tarefa => {
    if (filtroAtual === "ativas") return !tarefa.concluida;
    if (filtroAtual === "concluidas") return tarefa.concluida;
    return true;
  });

  tarefasFiltradas.forEach(tarefa => {
    const item = document.createElement("li");
    item.className = "task-item";
    item.dataset.id = tarefa.id;

    const descricao = document.createElement("span");
    descricao.className = "task-description";
    descricao.textContent = tarefa.descricao;

    if (tarefa.concluida) {
      descricao.classList.add("completed");
      descricao.style.textDecoration = "line-through";
    }

    const acoes = document.createElement("div");
    acoes.className = "task-actions";

    const botaoAlternar = document.createElement("button");
    botaoAlternar.type = "button";
    botaoAlternar.className = "task-button";
    botaoAlternar.textContent = tarefa.concluida ? "Desfazer" : "Concluir";
    botaoAlternar.setAttribute(
      "aria-label",
      `${tarefa.concluida ? "Desfazer conclusão de" : "Concluir"}: ${tarefa.descricao}`
    );
    botaoAlternar.addEventListener("click", () => alternarTarefa(tarefa.id));

    const botaoExcluir = document.createElement("button");
    botaoExcluir.type = "button";
    botaoExcluir.className = "task-button delete";
    botaoExcluir.textContent = "Excluir";
    botaoExcluir.setAttribute("aria-label", `Excluir: ${tarefa.descricao}`);
    botaoExcluir.addEventListener("click", () => removerTarefa(tarefa.id));

    acoes.appendChild(botaoAlternar);
    acoes.appendChild(botaoExcluir);

    item.appendChild(descricao);
    item.appendChild(acoes);
    listaTarefas.appendChild(item);
  });

  // Estado vazio para cada filtro.
  if (tarefasFiltradas.length === 0) {
    const vazio = document.createElement("li");
    vazio.className = "empty-state";

    const icone = document.createElement("div");
    icone.className = "empty-icon";
    icone.textContent = filtroAtual === "concluidas" ? "✓" : "✦";

    const titulo = document.createElement("strong");
    const mensagem = document.createElement("span");

    if (tarefas.length === 0) {
      titulo.textContent = "Sua lista está vazia";
      mensagem.textContent = "Adicione sua primeira tarefa para começar.";
    } else if (filtroAtual === "ativas") {
      titulo.textContent = "Tudo em dia!";
      mensagem.textContent = "Não há tarefas ativas no momento.";
    } else if (filtroAtual === "concluidas") {
      titulo.textContent = "Ainda sem conclusões";
      mensagem.textContent = "As tarefas concluídas aparecerão aqui.";
    } else {
      titulo.textContent = "Nenhuma tarefa encontrada";
      mensagem.textContent = "Adicione uma tarefa para preencher sua lista.";
    }

    vazio.appendChild(icone);
    vazio.appendChild(titulo);
    vazio.appendChild(mensagem);
    listaTarefas.appendChild(vazio);
  }

  const quantidadeAtivas = tarefas.reduce(
    (total, tarefa) => total + (tarefa.concluida ? 0 : 1),
    0
  );

  contador.textContent = `${quantidadeAtivas} ativas de ${tarefas.length} tarefas`;

  const botoesFiltro = [
    { botao: filtroTodas, valor: "todas" },
    { botao: filtroAtivas, valor: "ativas" },
    { botao: filtroConcluidas, valor: "concluidas" }
  ];

  botoesFiltro.forEach(({ botao, valor }) => {
    const selecionado = filtroAtual === valor;
    botao.classList.toggle("active", selecionado);
    botao.setAttribute("aria-pressed", String(selecionado));
  });
}

formTarefa.addEventListener("submit", evento => {
  evento.preventDefault();
  adicionarTarefa();
});

botaoAdicionar.addEventListener("click", evento => {
  evento.preventDefault();
  adicionarTarefa();
});

filtroTodas.addEventListener("click", () => {
  filtroAtual = "todas";
  renderizar();
});

filtroAtivas.addEventListener("click", () => {
  filtroAtual = "ativas";
  renderizar();
});

filtroConcluidas.addEventListener("click", () => {
  filtroAtual = "concluidas";
  renderizar();
});

renderizar();
