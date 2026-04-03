const totalBalanceElement = document.getElementById('totalBalance');
const itemNameInput = document.getElementById('itemName');
const amountInput = document.getElementById('amount');
const categorySelect = document.getElementById('category');
const addTransactionButton = document.getElementById('addTransaction');
const transactionList = document.getElementById('transactionList');
const spendingChartElement = document.getElementById('spendingChart');

let transactions = JSON.parse(localStorage.getItem('transactions')) || [];

function updateUI() {
    let totalBalance = 0;
    const categoryTotals = {
        Food: 0,
        Transport: 0,
        Fun: 0
    };

    transactionList.innerHTML = ''; 

    transactions.forEach(transaction => {
        totalBalance += parseFloat(transaction.amount);
        categoryTotals[transaction.category] += parseFloat(transaction.amount);

        const li = document.createElement('li');
        li.innerHTML = `${transaction.name} - $${transaction.amount} (${transaction.category}) <button onclick="deleteTransaction('${transaction.name}')">Delete</button>`;
        transactionList.appendChild(li);
    });

    totalBalanceElement.textContent = `$${totalBalance.toFixed(2)}`;
    drawChart(categoryTotals);
}

function drawChart(categoryTotals) {
    const chart = new Chart(spendingChartElement, {
        type: 'pie',
        data: {
            labels: ['Food', 'Transport', 'Fun'],
            datasets: [{
                data: [categoryTotals.Food, categoryTotals.Transport, categoryTotals.Fun],
                backgroundColor: ['#FF6347', '#FFD700', '#32CD32']
            }]
        }
    });
}

addTransactionButton.addEventListener('click', () => {
    const itemName = itemNameInput.value.trim();
    const amount = parseFloat(amountInput.value);
    const category = categorySelect.value;

    if (itemName && !isNaN(amount) && amount > 0) {
        const newTransaction = {
            name: itemName,
            amount: amount.toFixed(2),
            category: category
        };

        transactions.push(newTransaction);
        localStorage.setItem('transactions', JSON.stringify(transactions));

        itemNameInput.value = '';
        amountInput.value = '';
        categorySelect.value = 'Food';

        updateUI();
    }
});

function deleteTransaction(transactionName) {
    transactions = transactions.filter(transaction => transaction.name !== transactionName);
    localStorage.setItem('transactions', JSON.stringify(transactions));
    updateUI();
}

updateUI();