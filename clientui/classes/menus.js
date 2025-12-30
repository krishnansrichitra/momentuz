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
    menuContainer.innerHTML = ""; // clear existing menus

    menuSet.groups.forEach(group => {

        // Create dropdown <li>
        const li = document.createElement("li");
        li.className = "nav-item dropdown";

        // Dropdown toggle
        const a = document.createElement("a");
        a.className = "nav-link dropdown-toggle";
        a.href = "#";
        a.setAttribute("role", "button");
        a.setAttribute("data-bs-toggle", "dropdown");
        a.textContent = group.name;

        // Dropdown menu <ul>
        const ul = document.createElement("ul");
        ul.className = "dropdown-menu";

        group.items.forEach(item => {
            const itemLi = document.createElement("li");

            const itemA = document.createElement("a");
            itemA.className = "dropdown-item";
            itemA.href = "#";
            itemA.textContent = item.label;
            itemA.onclick = () => loadPage(item.page);

            itemLi.appendChild(itemA);
            ul.appendChild(itemLi);
        });

        li.appendChild(a);
        li.appendChild(ul);
        menuContainer.appendChild(li);
    });
}
