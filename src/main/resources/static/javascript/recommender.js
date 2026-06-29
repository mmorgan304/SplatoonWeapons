document.querySelectorAll('.weapon-select').forEach((el) => {
    new TomSelect(el, {
        plugins: {
            remove_button: {title: 'Remove this weapon'}
        },
        maxItems: null,
        persist: false
    });
});

function teamValidation() {
    const alphaWarning = document.getElementById('alphaWarning');
    const bravoWarning = document.getElementById('bravoWarning');

    let alphaValid = true;
    let bravoValid = true;

    for (let i=1; i<= 4; i++){
        const alphaSelect = document.getElementById('alpha'+i);
        if (alphaSelect && alphaSelect.selectedOptions.length < 1) {
            alphaValid = false;
        }
    }

    for (let i = 1; i <= 4; i++) {
        const bravoSelect = document.getElementById('bravo' + i);
        if (bravoSelect && bravoSelect.selectedOptions.length !== 1) {
            bravoValid = false;
        }
    }

    if (!alphaValid) {
        if (alphaWarning) alphaWarning.innerHTML = '<p>All Alpha players must have at least one weapon selected.</p>';
    } else {
        if (alphaWarning) alphaWarning.innerHTML = '';
    }

    if (!bravoValid) {
        if (bravoWarning) bravoWarning.innerHTML = '<p>All Bravo players must have exactly one weapon selected.</p>';
    } else {
        if (bravoWarning) bravoWarning.innerHTML = '';
    }

    return alphaValid && bravoValid;
}

function submissionHandler(event) {
    event.preventDefault();
    if (!teamValidation()){
        return;
    }
    // clear form validation
    const alphaWarning = document.getElementById('alphaWarning');
    const bravoWarning = document.getElementById('bravoWarning');
    if (alphaWarning) alphaWarning.innerHTML = '';
    if (bravoWarning) bravoWarning.innerHTML = '';

    const form = event.target;
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    const statusContainer = document.getElementById('status-message');
    statusContainer.innerHTML = '<p class="loading">Analyzing team compositions...</p>';

    fetch('/generateTeam?' + params.toString(), {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            const team = data.recommendedTeam;
            const images = data.weaponImages || {};

            const hoverRoles = data.hoverRoles || {}
            const explanationDiv = document.getElementById('explanation');

            const p1Weapon = team.player1Pool[0];
            const p2Weapon = team.player2Pool[0];
            const p3Weapon = team.player3Pool[0];
            const p4Weapon = team.player4Pool[0];

            // Set Alt text
            document.getElementById('recPlayer1').alt = p1Weapon;
            document.getElementById('recPlayer2').alt = p2Weapon;
            document.getElementById('recPlayer3').alt = p3Weapon;
            document.getElementById('recPlayer4').alt = p4Weapon;

            // Set Image Src
            document.getElementById('recPlayer1').src = images[p1Weapon] || '../weaponImages/Spl3_Weapon_0.png';
            document.getElementById('recPlayer2').src = images[p2Weapon] || '../weaponImages/Spl3_Weapon_0.png';
            document.getElementById('recPlayer3').src = images[p3Weapon] || '../weaponImages/Spl3_Weapon_0.png';
            document.getElementById('recPlayer4').src = images[p4Weapon] || '../weaponImages/Spl3_Weapon_0.png';

            document.getElementById('tipPlayer1').innerHTML = `<strong>${p1Weapon}</strong><br>${hoverRoles[p1Weapon] || 'Utilize standard kit spacing and play around teammates.'}`;
            document.getElementById('tipPlayer2').innerHTML = `<strong>${p2Weapon}</strong><br>${hoverRoles[p2Weapon] || 'Utilize standard kit spacing and play around teammates.'}`;
            document.getElementById('tipPlayer3').innerHTML = `<strong>${p3Weapon}</strong><br>${hoverRoles[p3Weapon] || 'Utilize standard kit spacing and play around teammates.'}`;
            document.getElementById('tipPlayer4').innerHTML = `<strong>${p4Weapon}</strong><br>${hoverRoles[p4Weapon] || 'Utilize standard kit spacing and play around teammates.'}`;

            const advantagesList = document.getElementById('advantages-list');
            const deficitsList = document.getElementById('deficits-list');

            // Clear previous results
            advantagesList.innerHTML = '';
            deficitsList.innerHTML = '';

            const advantages = data.features.advantages || [];
            const deficits = data.features.deficits || [];

            if (advantages.length === 0) {
                const li = document.createElement('li');
                li.style.color = '#6c757d';
                li.style.padding = "8px 0";
                li.style.fontStyle = "italic";
                li.textContent = "No notable advantages discovered.";
                advantagesList.appendChild(li);
            } else {
                advantages.forEach(f => {
                    const li = document.createElement('li');
                    li.className = 'list-group-item';
                    li.style.padding = "8px 0";
                    li.textContent = "✅ " + f;
                    advantagesList.appendChild(li);
                });
            }

            // Populate Deficits
            if (deficits.length === 0) {
                const li = document.createElement('li');
                li.style.color = '#6c757d';
                li.style.padding = "8px 0";
                li.style.fontStyle = "italic";
                li.textContent = "✨ No noticeable deficits!";
                deficitsList.appendChild(li);
            } else {
                deficits.forEach(f => {
                    const li = document.createElement('li');
                    li.className = 'list-group-item';
                    li.style.padding = "8px 0";
                    li.textContent = "❌ " + f;
                    deficitsList.appendChild(li);
                });
            }

            if (data.explanation) {
                explanationDiv.textContent = data.explanation;
            }

            // Clear loading
            const statusMsg = document.getElementById('status-message');
            if (statusMsg) statusMsg.innerHTML = '';

            document.getElementById('recommendation').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            statusContainer.innerHTML = '<p style="color: red;">Failed to generate recommendation.</p>';
        });
}