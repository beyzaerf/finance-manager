document.getElementById('expenseForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const amount = document.getElementById('amount').value;
    const description = document.getElementById('description').value;
    const date = document.getElementById('date').value;

    fetch('/expenses', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ amount, description, date })
    }).then(response => response.json())
        .then(data => {
            console.log(data);
            loadExpenses();
        });
});

function loadExpenses() {
    fetch('/expenses')
        .then(response => response.json())
        .then(data => {
            const expensesList = document.getElementById('expensesList');
            expensesList.innerHTML = '<ul>' + data.map(expense => `<li>${expense.date}: ${expense.amount} - ${expense.description}</li>`).join('') + '</ul>';
        });
}

loadExpenses();

// Delete Expense
document.getElementById('deleteExpenseForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const expenseId = document.getElementById('expenseIdToDelete').value;

    fetch('/expenses/' + expenseId, {
        method: 'DELETE'
    }).then(() => {
        loadExpenses(); // Reload expenses list if needed
    });
});

// Calculate Monthly Expenses
document.getElementById('calculateMonthlyExpensesForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const month = document.getElementById('monthForExpenses').value;

    fetch('/expenses/monthly-total?yearMonth=' + month)
        .then(response => response.json())
        .then(total => {
            alert('Total expenses for the month: ' + total);
        });
});

// Add Income
document.getElementById('incomeForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // Gather income data from form...
    // Similar to addExpense, send a POST request to '/incomes' endpoint
});

// Delete Income
document.getElementById('deleteIncomeForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // Get income ID and send a DELETE request to '/incomes/{id}' endpoint
});

// Calculate Monthly Income
document.getElementById('calculateMonthlyIncomeForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // Get the month and send a GET request to '/incomes/monthly-total?yearMonth=' + month
    // Similar to calculateMonthlyExpenses
});

function loadIncomes() {
    fetch('/income')
        .then(response => response.json())
        .then(data => {
            const incomeList = document.getElementById('incomeList');
            incomeList.innerHTML = '<ul>' + data.map(income => `<li>${income.date}: ${income.amount} - ${income.description}</li>`).join('') + '</ul>';
        });
}

loadExpenses();
loadIncomes();