// DOM Elements
const balance = document.getElementById('balance');
const money_plus = document.getElementById('money-plus');
const money_minus = document.getElementById('money-minus');
const list = document.getElementById('list');
const form = document.getElementById('form');
const text = document.getElementById('text');
const amount = document.getElementById('amount');

const user = JSON.parse(localStorage.getItem("user"));
const username = user && user.name ? user.name : "Guest";
const userEmail = user && user.email ? user.email : null;

document.getElementById("usernameDisplay").innerText = `Welcome, ${username} 👋`;

let transactions = [];

// Fetch transactions from backend
async function fetchTransactions() {
    try {
        const res = await fetch(`http://localhost:8080/api/transactions/${userEmail}`);
        transactions = await res.json();
        renderTransactions();
    } catch (err) {
        console.error("Error fetching transactions:", err);
    }
}

// Add Transaction
async function addTransaction(e) {
    e.preventDefault();

    if (!text.value.trim() || !amount.value.trim()) {
        alert('Please add text and amount');
        return;
    }

    if (!userEmail) {
        alert("User not found! Please log in again.");
        return;
    }

    const transaction = {
        text: text.value.trim(),
        amount: +amount.value
    };

    try {
        const res = await fetch(`http://localhost:8080/api/transactions/${userEmail}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(transaction)
        });

        if (!res.ok) throw new Error("Failed to add transaction");

        const savedTransaction = await res.json();
        transactions.push(savedTransaction); // ✅ Add to array
        renderTransactions(); // ✅ Refresh UI
    } catch (err) {
        console.error("Error adding transaction:", err);
        alert("Error adding transaction. Check console for details.");
    }

    text.value = '';
    amount.value = '';
}

// Remove Transaction
async function removeTransaction(id) {
    try {
        await fetch(`http://localhost:8080/api/transactions/${id}`, {
            method: "DELETE"
        });
        transactions = transactions.filter(t => t.id !== id);
        renderTransactions();
    } catch (err) {
        console.error("Error deleting transaction:", err);
    }
}

// Render Transactions
function renderTransactions() {
    list.innerHTML = '';
    transactions.forEach(addTransactionDOM);
    updateValues();
}

function addTransactionDOM(transaction) {
    const sign = transaction.amount < 0 ? '-' : '+';

    const item = document.createElement('li');
    item.classList.add(transaction.amount < 0 ? 'minus' : 'plus');
    item.innerHTML = `
        ${transaction.text} <span>${sign}₹${Math.abs(transaction.amount)}</span>
        <button class="delete-btn" onclick="removeTransaction(${transaction.id})">x</button>
    `;
    list.appendChild(item);
}

function updateValues() {
    const amounts = transactions.map(t => t.amount);
    const total = amounts.reduce((acc, val) => acc + val, 0).toFixed(2);
    const income = amounts.filter(val => val > 0).reduce((acc, val) => acc + val, 0).toFixed(2);
    const expense = (
        amounts.filter(val => val < 0).reduce((acc, val) => acc + val, 0) * -1
    ).toFixed(2);

    balance.innerText = `₹${total}`;
    money_plus.innerText = `+₹${income}`;
    money_minus.innerText = `-₹${expense}`;
}

form.addEventListener('submit', addTransaction);

// Initialize
fetchTransactions();
