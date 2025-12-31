class MenuItem {
    constructor(id, label, page, icon) {
        this.id = id;
        this.label = label;
        this.page = page;   // page to load in iframe
        this.icon = icon;
    }
}

class MenuGroup {
    constructor(id, name, items = []) {
        this.id = id;
        this.name = name;
        this.items = items; // List<MenuItem>
    }

    addItem(menuItem) {
        this.items.push(menuItem);
    }
}

class MenuSet {
    constructor(groups = []) {
        this.groups = groups; // List<MenuGroup>
    }

    addGroup(group) {
        this.groups.push(group);
    }
}

function renderMenus(menuSet) {
    const menuContainer = document.getElementById("menuContainer");
    menuContainer.innerHTML = "";

    menuSet.groups.forEach(group => {

        const li = document.createElement("li");
        li.className = "nav-item dropdown";

        const a = document.createElement("a");
        a.className = "nav-link dropdown-toggle";
        a.href = "#";
        a.setAttribute("role", "button");
        a.setAttribute("data-bs-toggle", "dropdown");
        a.textContent = group.name;

        const ul = document.createElement("ul");
        ul.className = "dropdown-menu";

        group.items.forEach((item, index) => {

            // Menu item
            const itemLi = document.createElement("li");
            const itemA = document.createElement("a");

            itemA.className = "dropdown-item";
            itemA.href = "#";
            itemA.textContent = item.label;
            itemA.onclick = () => loadPage(item.page);

            itemLi.appendChild(itemA);
            ul.appendChild(itemLi);

            // Separator (except after last item)
            if (index < group.items.length - 1) {
                const dividerLi = document.createElement("li");
                const divider = document.createElement("hr");
                divider.className = "dropdown-divider";

                dividerLi.appendChild(divider);
                ul.appendChild(dividerLi);
            }
        });

        li.appendChild(a);
        li.appendChild(ul);
        menuContainer.appendChild(li);
    });
}



function buildMenuSetFromApi(data) {
    const menuSet = new MenuSet();

    data.groups.forEach(g => {
        const group = new MenuGroup(g.id, g.name);

        g.items.forEach(i => {
            const item = new MenuItem(
                i.id,
                i.label,
                i.page,
                i.icon
            );
            group.addItem(item);
        });

        menuSet.addGroup(group);
    });

    return menuSet;
}


function loadMenusFromBackend() {
    axios.get("http://localhost:8080/api/menu/getMenus")
        .then(res => renderMenus(buildMenuSetFromApi(res.data)))
        .catch(err => {

            console.log(err);
            if (err.response && err.response.status === 403) {
                localStorage.removeItem("jwtToken");
                window.location.href = "login.html";
            }
        });
}
