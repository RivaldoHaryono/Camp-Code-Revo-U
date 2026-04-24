// =====================
// GREETING + TIME
// =====================
function updateTime() {
  const now = new Date();
  const hour = now.getHours();
  let greeting = "Hello";

  if (hour < 12) greeting = "Good Morning";
  else if (hour < 18) greeting = "Good Afternoon";
  else greeting = "Good Evening";

  document.getElementById("greetingText").innerText = greeting;
  document.getElementById("time").innerText = now.toLocaleString();
}

setInterval(updateTime, 1000);

// =====================
// TIMER (25 MINUTES)
// =====================
let time = 1500;
let interval;

function updateTimerDisplay() {
  let minutes = Math.floor(time / 60);
  let seconds = time % 60;
  document.getElementById("timer").innerText =
    `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
}

function startTimer() {
  interval = setInterval(() => {
    if (time > 0) {
      time--;
      updateTimerDisplay();
    }
  }, 1000);
}

function stopTimer() {
  clearInterval(interval);
}

function resetTimer() {
  time = 1500;
  updateTimerDisplay();
}

// =====================
// TODO LIST (LOCAL STORAGE)
// =====================
let tasks = JSON.parse(localStorage.getItem("tasks")) || [];

function saveTasks() {
  localStorage.setItem("tasks", JSON.stringify(tasks));
}

function renderTasks() {
  let list = document.getElementById("taskList");
  list.innerHTML = "";

  tasks.forEach((task, index) => {
    let li = document.createElement("li");
    li.innerHTML = `
      ${task.text}
      <button onclick="deleteTask(${index})">X</button>
    `;
    list.appendChild(li);
  });
}

function addTask() {
  let input = document.getElementById("taskInput");
  if (!input.value) return;

  tasks.push({ text: input.value });
  input.value = "";
  saveTasks();
  renderTasks();
}

function deleteTask(index) {
  tasks.splice(index, 1);
  saveTasks();
  renderTasks();
}

renderTasks();

// =====================
// QUICK LINKS
// =====================
let links = JSON.parse(localStorage.getItem("links")) || [];

function saveLinks() {
  localStorage.setItem("links", JSON.stringify(links));
}

function renderLinks() {
  let container = document.getElementById("linkList");
  container.innerHTML = "";

  links.forEach(link => {
    let a = document.createElement("a");
    a.href = link.url;
    a.innerText = link.name;
    a.target = "_blank";
    container.appendChild(a);
    container.appendChild(document.createElement("br"));
  });
}

function addLink() {
  let name = document.getElementById("linkName").value;
  let url = document.getElementById("linkURL").value;

  if (!name || !url) return;

  links.push({ name, url });
  saveLinks();
  renderLinks();
}

renderLinks();