document.querySelectorAll('.weapon-select').forEach((el) => {
    new TomSelect(el, {
        plugins: {
            remove_button: {title: 'Remove this weapon'}
        },
        maxItems: null,
        persist: false
    });
});

function submissionHandler(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    // Target the specific status area, not the whole container
    const statusContainer = document.getElementById('status-message');
    statusContainer.innerHTML = '<p class="loading">Analyzing team compositions...</p>';

    fetch('/generateTeam?' + params.toString(), { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            const team = data.recommendedTeam;
            const featuresList = document.getElementById('features-list');
            const explanationDiv = document.getElementById('explanation');

            document.getElementById('recPlayer1').alt = team.player1Pool;
            document.getElementById('recPlayer2').alt = team.player2Pool;
            document.getElementById('recPlayer3').alt = team.player3Pool;
            document.getElementById('recPlayer4').alt = team.player4Pool;

            featuresList.innerHTML = '';
            data.features.forEach(featureText => {
                const li = document.createElement('li');
                li.className = 'list-group-item';
                li.textContent = featureText;
                featuresList.appendChild(li);
            });

            explanationDiv.textContent = data.explanation;

            // Clear loading
            document.getElementById('status-message').innerHTML = '';
            document.getElementById('recommendation').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            statusContainer.innerHTML = '<p style="color: red;">Failed to generate recommendation.</p>';
        });
}