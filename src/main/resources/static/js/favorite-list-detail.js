document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.edit-list-btn').forEach(function(button) {
        button.addEventListener('click', function() {
            const id = this.dataset.id;
            const name = this.dataset.name;
            const description = this.dataset.description;

            document.getElementById('editListId').value = id;
            document.getElementById('editListName').value = name;
            document.getElementById('editListDescription').value = description;

            const modal = new bootstrap.Modal(document.getElementById('editListModal'));
            modal.show();
        });
    });

    document.getElementById('saveListChangesBtn').addEventListener('click', function() {
        const id = document.getElementById('editListId').value;
        const name = document.getElementById('editListName').value;
        const description = document.getElementById('editListDescription').value;

        if (!name) {
            alert('Please enter a list name');
            return;
        }

        fetch('/api/favorite-lists/' + id + '/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                name: name,
                description: description
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.reload();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            alert('Failed to update list');
        });
    });


    document.querySelectorAll('.delete-list-btn').forEach(function(button) {
        button.addEventListener('click', function() {
            const id = this.dataset.id;
            const name = this.dataset.name;

            document.getElementById('deleteListId').value = id;
            document.getElementById('deleteListName').textContent = name;

            const modal = new bootstrap.Modal(document.getElementById('deleteListModal'));
            modal.show();
        });
    });


    document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
        const id = document.getElementById('deleteListId').value;

        fetch('/api/favorite-lists/' + id + '/delete', {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = '/favorite-lists';
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            alert('Failed to delete list');
        });
    });


    document.querySelectorAll('.remove-from-list').forEach(function(button) {
        button.addEventListener('click', function() {
            const animeId = this.dataset.animeId;
            const listId = this.dataset.listId;

            if (confirm('Are you sure you want to remove this anime from the list?')) {
                fetch('/api/favorite-lists/' + listId + '/remove-anime', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        animeId: animeId
                    })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert(data.message);
                    }
                })
                .catch(error => {
                    alert('Failed to remove anime from list');
                });
            }
        });
    });

    document.querySelectorAll('.toggle-favorite').forEach(function(button) {
        button.addEventListener('click', function() {
            const animeId = this.dataset.id;
            const isFavorited = this.dataset.favorited === 'true';

            let url, method;
            if (isFavorited) {
                url = '/api/favorites/remove';
                method = 'DELETE';
            } else {
                url = '/api/favorites/add';
                method = 'POST';
            }

            fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    animeId: animeId
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    if (isFavorited) {
                        this.textContent = 'Add to Favorites';
                        this.classList.remove('btn-secondary');
                        this.classList.add('btn-outline-secondary');
                        this.dataset.favorited = 'false';
                    } else {
                        this.textContent = 'Remove from Favorites';
                        this.classList.remove('btn-outline-secondary');
                        this.classList.add('btn-secondary');
                        this.dataset.favorited = 'true';
                    }
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                alert('Failed to update favorite status');
            });
        });
    });
});
