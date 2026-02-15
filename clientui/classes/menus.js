class MenuItem {
    constructor(id, label, page, icon,hasChildren,parentItem) {
        this.id = id;
        this.label = label;
        this.page = page;   // page to load in iframe
        this.icon = icon;
        this.hasChildren=hasChildren;
        this.parentItem=parentItem;

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

function getSubMenus(allMenus, parentItem)
{

  const children = allMenus.filter(item => item.parentItem === parentItem);
  return children;


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
        
            itemA.textContent = item.label;
            if (item.parentItem !== null) return;
            if (item.hasChildren !== null &&   item.hasChildren === true) {
                itemLi.className="dropdown-submenu dropend";
                itemA.className = "dropdown-item dropdown-toggle";
                itemA.href = "#";
                let children = getSubMenus(group.items,item.id);
                console.log(JSON.stringify(children));
                const subul = document.createElement("ul");
                subul.className = "dropdown-menu";
                for (let child of children) {
                        const subitemLi = document.createElement("li");
                        const subitemA = document.createElement("a"); 
                        subitemA.className = "dropdown-item";
                        subitemA.textContent=child.label;
                        subitemA.href = "#";
                        subitemA.onclick = () => loadPage(child.page);
                        subitemLi.appendChild(subitemA);
                        subul.appendChild(subitemLi);
                }
                itemLi.appendChild(itemA);
                itemLi.append(subul);

            }else{
                itemA.className = "dropdown-item";
                itemA.href = "#";
                itemA.onclick = () => loadPage(item.page);
                itemLi.appendChild(itemA);
                
            }
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
                i.icon,
                i.hasChildren,
                i.parentItem
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


function openSettings()
{


}


