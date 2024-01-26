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
