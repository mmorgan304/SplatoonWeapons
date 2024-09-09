//event listeners for collapsible buttons
document.addEventListener('DOMContentLoaded', function () {
    var coll = document.getElementsByClassName("collapsible");

    for (var i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            // Close any currently open collapsible content
            // var openContent = document.querySelector(".collapsible-content.show");
            // if (openContent && openContent !== this.nextElementSibling) {
            //     openContent.style.maxHeight = null;
            //     openContent.classList.remove("show");
            // }

            // Toggle the clicked collapsible section
            var content = this.nextElementSibling;
            if (content.classList.contains("show")) {
                content.style.maxHeight = null; // Collapse it
                content.classList.remove("show");
            } else {
                content.classList.add("show");
                content.style.maxHeight = content.scrollHeight + "px"; // Expand it
            }
        });
    }

    // Close all collapsible sections by default
    var allCollapsibles = document.getElementsByClassName("collapsible-content");
    for (var j = 0; j < allCollapsibles.length; j++) {
        allCollapsibles[j].classList.remove("show");
    }

    // Automatically open the default tab
    var defaultTab = document.getElementById("defaultOpen");
    if (defaultTab) {
        defaultTab.click();
    }
});


document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("defaultOpen").click(); // Automatically click the default tab
});

function openSearch(evt, weaponSearch) {
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(weaponSearch).style.display = "block";
    evt.currentTarget.className += " active";
}

function submissionHandler(event) {
    event.preventDefault();
    const form = event.target;

    const selectedTypes = form.querySelectorAll('input[name="selectedTypes"]:checked').length > 0 ? Array.from(form.querySelectorAll('input[name="selectedTypes"]:checked')).map(checkbox => checkbox.value) : [];

    const selectedSubweapons = form.querySelectorAll('input[name="selectedSubweapons"]:checked').length > 0 ? Array.from(form.querySelectorAll('input[name="selectedSubweapons"]:checked')).map(checkbox => checkbox.value) : [];

    const selectedSpecials = form.querySelectorAll('input[name="selectedSpecials"]:checked').length > 0 ? Array.from(form.querySelectorAll('input[name="selectedSpecials"]:checked')).map(checkbox => checkbox.value) : [];

    const selectedWeights = form.querySelectorAll('input[name="selectedWeights"]:checked').length > 0 ? Array.from(form.querySelectorAll('input[name="selectedWeights"]:checked')).map(checkbox => checkbox.value) : [];

    const selectedWeapons = Array.from(form.querySelectorAll('input[name="selectedWeapons"]:checked'))
        .map(checkbox => checkbox.value);
    const dupesExcluded = form.querySelector('input[name="dupesExcluded"]') ? form.querySelector('input[name="dupesExcluded"]').checked : false;
    const isInclusionSearch = form.querySelector('input[name="isInclusionSearch"]').value;

    const params = new URLSearchParams();

    selectedTypes.forEach(value => params.append('weaponTypeIds', value));
    selectedSubweapons.forEach(value => params.append('subweaponIds', value));
    selectedSpecials.forEach(value => params.append('specialWeaponIds', value));
    selectedWeights.forEach(value => params.append('weightClassIds', value));
    selectedWeapons.forEach(value => params.append('weaponIds', value));
    params.append('isDuplicate', dupesExcluded);
    params.append('isInclusionSearch', isInclusionSearch);

    fetch('/generateRandomWeapon?' + params.toString(), {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('weaponImage').src = '/weaponImages/Spl3_Weapon_' + data.id + '.png';
            document.getElementById('weaponImage').alt = data.weaponName;

            document.getElementById('subweaponImage').src = '/subweaponImages/Spl3_sub_' + data.subweapon.id + '_b.png';
            document.getElementById('subweaponImage').alt = data.subweapon.subweaponName;

            document.getElementById('specialWeaponImage').src = '/specialWeaponImages/Spl3_spec_' + data.specialWeapon.id + '_b.png';
            document.getElementById('specialWeaponImage').alt = data.specialWeapon.specialWeaponName;

            document.getElementById('randomWeapon').innerText = data.weaponName;
        })
        .catch(error => {
            console.error('Error:', error)
        });
}