const removeButtons = document.querySelectorAll('.remove');

removeButtons.forEach((button) => {
    button.addEventListener('click', (event) => {
        const order = event.target.closest('.order');
        order.remove();
    });
});
