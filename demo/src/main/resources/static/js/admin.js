function fetchInitialData() {
    fetchTrainers();
    fetchSchedules();
    fetchMembers();
    fetchFaculties();
}

function updateList(data, listId, formSetter) {
    const tableBody = document.getElementById(listId);
    tableBody.innerHTML = '';
    data.forEach(item => {
        const row = tableBody.insertRow();
        for (const key in item) {
            const cell = row.insertCell();
            cell.textContent = item[key];
        }
        const editCell = row.insertCell();
        editCell.appendChild(createEditButton(item, formSetter)); // Ensure formSetter is being used here

        const deleteCell = row.insertCell();
        deleteCell.appendChild(createDeleteButton(item, listId, () => fetchInitialData()));
    });
}



function createEditButton(item, setForm) {
    const button = document.createElement('button');
    button.textContent = 'Edit';
    button.onclick = () => setForm(item);
    return button;
}

// function createDeleteButton(item, listId, fetchFunction) {
//     const modelType = listId.replace('-list', 's'); // Append 's' to make it plural correctly
//     const button = document.createElement('button');
//     button.textContent = 'Delete';
//     button.onclick = () => {
//         const url = `/api/${modelType}/${item.id}`; // Correctly forming the URL here
//         fetch(url, { method: 'DELETE' })
//             .then(response => {
//                 if (!response.ok) throw new Error(`Failed to delete ${modelType.slice(0, -1)} with ID ${item.id}`);
//                 fetchFunction();
//             })
//             .catch(error => console.error('Error:', error));
//     };
//     return button;
// }

function getPluralModelType(modelType) {
    if (modelType === 'faculty') {
        return 'faculties';
    }
    return modelType + 's';  // Default to just adding 's'
}

function createDeleteButton(item, listId, fetchFunction) {
    const singularModelType = listId.replace('-list', '');  // Strip '-list'
    const modelType = getPluralModelType(singularModelType);  // Correctly handle irregular plurals
    const button = document.createElement('button');
    button.textContent = 'Delete';
    button.onclick = () => {
        const url = `/api/${modelType}/${item.id}`;
        fetch(url, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) throw new Error(`Failed to delete ${singularModelType} with ID ${item.id}`);
                fetchFunction();
            })
            .catch(error => console.error('Error:', error));
    };
    return button;
}


function setTrainerForm(trainer) {
    document.getElementById('trainer-id').value = trainer.id;
    document.getElementById('trainer-name').value = trainer.name;
}

function setScheduleForm(schedule) {
    document.getElementById('schedule-id').value = schedule.id;
    document.getElementById('schedule-day').value = schedule.day;
    document.getElementById('schedule-time').value = schedule.time;
}

function setMemberForm(member) {
    document.getElementById('member-id').value = member.id;
    document.getElementById('member-name').value = member.name;
    document.getElementById('member-email').value = member.email;
    document.getElementById('member-package').value = member.membership_package;
}

function setFacultyForm(faculty) {
    document.getElementById('faculty-id').value = faculty.id;
    document.getElementById('faculty-name').value = faculty.name;
}

function resetTrainerForm() {
    document.getElementById('trainer-id').value = '';
    document.getElementById('trainer-name').value = '';
}

function resetScheduleForm() {
    document.getElementById('schedule-id').value = '';
    document.getElementById('schedule-day').value = '';
    document.getElementById('schedule-time').value = '';
}

function resetMemberForm() {
    document.getElementById('member-id').value = '';
    document.getElementById('member-name').value = '';
    document.getElementById('member-email').value = '';
    document.getElementById('member-package').value = '';
}

function resetFacultyForm() {
    document.getElementById('faculty-id').value = '';
    document.getElementById('faculty-name').value = '';
}
function fetchTrainers() {
    fetch('/api/trainers/all')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch trainers');
            return response.json();
        })
        .then(data => updateList(data, 'trainer-list', setTrainerForm)) // Ensure setTrainerForm is passed here
        .catch(error => console.error('Error fetching trainers:', error));
}


// Fetch Schedules
function fetchSchedules() {
    fetch('/api/schedules/all')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch schedules');
            return response.json();
        })
        .then(data => updateList(data, 'schedule-list', setScheduleForm))
        .catch(error => console.error('Error fetching schedules:', error));
}

// Fetch Members
function fetchMembers() {
    fetch('/api/members')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch members');
            return response.json();
        })
        .then(data => updateList(data, 'member-list', setMemberForm))
        .catch(error => console.error('Error fetching members:', error));
}

// Fetch Faculties
function fetchFaculties() {
    fetch('/api/faculties/all')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch faculties');
            return response.json();
        })
        .then(data => updateList(data, 'faculty-list', setFacultyForm))
        .catch(error => console.error('Error fetching faculties:', error));
}

function submitTrainerForm() {
    const id = document.getElementById('trainer-id').value;
    const name = document.getElementById('trainer-name').value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/trainers/${id}` : '/api/trainers/';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to submit trainer data');
            return response.json();
        })
        .then(() => {
            resetTrainerForm();
            fetchTrainers(); // Reload the list
        })
        .catch(error => console.error('Error:', error));
    return false; // Prevent default form submission
}
// Submit Schedule Form
function submitScheduleForm() {
    const id = document.getElementById('schedule-id').value;
    const day = document.getElementById('schedule-day').value;
    const time = document.getElementById('schedule-time').value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/schedules/${id}` : '/api/schedules/';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ day, time })
    })
        .then(response => response.ok ? response.json() : Promise.reject('Failed to submit schedule data'))
        .then(() => {
            resetScheduleForm();
            fetchSchedules(); // Reload the list
        })
        .catch(error => console.error('Error:', error));

    return false;
}

// Submit Member Form
function submitMemberForm() {
    const id = document.getElementById('member-id').value;
    const name = document.getElementById('member-name').value;
    const email = document.getElementById('member-email').value;
    const membership_package = document.getElementById('member-package').value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/members/${id}` : '/api/members/';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, membership_package })
    })
        .then(response => response.ok ? response.json() : Promise.reject('Failed to submit member data'))
        .then(() => {
            resetMemberForm();
            fetchMembers(); // Reload the list
        })
        .catch(error => console.error('Error:', error));

    return false;
}

// Submit Faculty Form
function submitFacultyForm() {
    const id = document.getElementById('faculty-id').value;
    const name = document.getElementById('faculty-name').value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/faculties/${id}` : '/api/faculties/';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
    })
        .then(response => response.ok ? response.json() : Promise.reject('Failed to submit faculty data'))
        .then(() => {
            resetFacultyForm();
            fetchFaculties(); // Reload the list
        })
        .catch(error => console.error('Error:', error));

    return false;
}
