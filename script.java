// ===== State =====
let transactions = JSON.parse(localStorage.getItem('transactions') || '[]');
let categories = JSON.parse(localStorage.getItem('categories') || '["Food","Transport","Fun"]');
let chart = null;

// ===== DOM =====
const totalBalanceEl = document.getElementById('totalBalance');
const itemNameEl = document.getElementById('itemName');
const amountEl = document.getElementById('amount');
const categoryEl = document.getElementById('category');
const addBtn = document.getElementById('addBtn');
const transactionListEl = document.getElementById('transactionList');
const themeToggle = document.getElementById('themeToggle');
const sortSelect = document.getElementById('sortSelect');
const addCatBtn = document.getElementById('addCatBtn');
const customCatRow = document.getElementById('customCatRow');
const customCatInput = document.getElementById('customCatInput');
const saveCatBtn = document.getElementById('saveCatBtn');
const chartEmptyEl = document.getElementById('chartEmpty');

// ===== Init =====
function init() {
  renderCategories();
  renderAll();
  loadTheme();
}

// ===== Theme =====
function loadTheme() {
  const theme = localStorage.getItem('theme') || 'light';
  document.documentElement.setAttribute('data-theme', theme);
  themeToggle.textContent = theme === 'dark' ? '☀️' : '🌙';
}

themeToggle.addEventListener('click', () => {
  const current = document.documentElement.getAttribute('data-theme');
  const next = current === 'dark' ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', next);
  localStorage.setItem('theme', next);
  themeToggle.textContent = next === 'dark' ? '☀️' : '🌙';
});

// ===== Categories =====
function renderCategories() {
  const current = categoryEl.value;
  categoryEl.innerHTML = '';
  categories.forEach(cat => {
    const opt = document.createElement('option');
    opt.value = cat;
    opt.textContent = cat;
    categoryEl.appendChild(opt);
  });
  if (current && categories.includes(current)) categoryEl.value = current;
}

addCatBtn.addEventListener('click', () => {
  customCatRow.classList.toggle('hidden');
  if (!customCatRow.classList.contains('hidden')) customCatInput.focus();
});

saveCatBtn.addEventListener('click', () => {
  const name = customCatInput.value.trim();
  if (!name) return alert('Please enter a category name.');
  if (categories.map(c => c.toLowerCase()).includes(name.toLowerCase())) {
    return alert('Category already exists!');
  }
  categories.push(name);
  localStorage.setItem('categories', JSON.stringify(categories));
  renderCategories();
  categoryEl.value = name;
  customCatInput.value = '';
  customCatRow.classList.add('hidden');
});

// ===== Add Transaction =====
addBtn.addEventListener('click', () => {
  const name = itemNameEl.value.trim();
  const amount = parseFloat(amountEl.value);
  const category = categoryEl.value;

  if (!name || isNaN(amount) || amount <= 0 || !category) {
    alert('Please fill in all fields correctly.');
    return;
  }

  const transaction = {
    id: Date.now(),
    name,
    amount,
    category,
    timestamp: Date.now()
  };

  transactions.unshift(transaction);
  saveTransactions();
  renderAll();

  itemNameEl.value = '';
  amountEl.value = '';
  itemNameEl.focus();
});

// ===== Delete =====
function deleteTransaction(id) {
  transactions = transactions.filter(t => t.id !== id);
  saveTransactions();
  renderAll();
}

// ===== Render =====
function renderAll() {
  renderBalance();
  renderList();
  renderChart();
}

function renderBalance() {
  const total = transactions.reduce((sum, t) => sum + t.amount, 0);
  totalBalanceEl.textContent = 'Rp ' + total.toLocaleString('id-ID');
}

function getSortedTransactions() {
  const sort = sortSelect.value;
  const list = [...transactions];
  if (sort === 'amount-asc') list.sort((a, b) => a.amount - b.amount);
  else if (sort === 'amount-desc') list.sort((a, b) => b.amount - a.amount);
  else if (sort === 'category') list.sort((a, b) => a.category.localeCompare(b.category));
  return list;
}

function renderList() {
  const sorted = getSortedTransactions();
  if (sorted.length === 0) {
    transactionListEl.innerHTML = '<p class="empty-state">No transactions yet. Add one above!</p>';
    return;
  }
  transactionListEl.innerHTML = sorted.map(t => `
    <div class="transaction-item">
      <div class="item-info">
        <div class="item-name">${escapeHtml(t.name)}</div>
        <div class="item-amount">Rp ${t.amount.toLocaleString('id-ID')}</div>
        <span class="item-category">${escapeHtml(t.category)}</span>
      </div>
      <button class="btn-delete" onclick="deleteTransaction(${t.id})">Delete</button>
    </div>
  `).join('');
}

function renderChart() {
  const ctx = document.getElementById('spendingChart').getContext('2d');

  // Aggregate by category
  const totals = {};
  transactions.forEach(t => {
    totals[t.category] = (totals[t.category] || 0) + t.amount;
  });

  const labels = Object.keys(totals);
  const data = Object.values(totals);

  if (labels.length === 0) {
    chartEmptyEl.classList.remove('hidden');
    if (chart) { chart.destroy(); chart = null; }
    return;
  }
  chartEmptyEl.classList.add('hidden');

  const colors = generateColors(labels.length);

  if (chart) chart.destroy();
  chart = new Chart(ctx, {
    type: 'pie',
    data: {
      labels,
      datasets: [{
        data,
        backgroundColor: colors,
        borderWidth: 2,
        borderColor: '#fff'
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom',
          labels: {
            color: getComputedStyle(document.documentElement).getPropertyValue('--text') || '#1a1a2e',
            padding: 12,
            font: { size: 12 }
          }
        },
        tooltip: {
          callbacks: {
            label: ctx => ` ${ctx.label}: Rp ${ctx.parsed.toLocaleString('id-ID')}`
          }
        }
      }
    }
  });
}

function generateColors(n) {
  const base = ['#4f46e5','#10b981','#f59e0b','#ef4444','#3b82f6','#8b5cf6','#ec4899','#06b6d4','#84cc16','#f97316'];
  const result = [];
  for (let i = 0; i < n; i++) result.push(base[i % base.length]);
  return result;
}

function saveTransactions() {
  localStorage.setItem('transactions', JSON.stringify(transactions));
}

function escapeHtml(str) {
  return str.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}

sortSelect.addEventListener('change', renderList);

// ===== Start =====
init();