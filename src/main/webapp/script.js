var editedCompanyElement;
var editWindow = document.getElementById("edit");
var nameInput = document.getElementById("name");
var parentInput = document.getElementById("parent");
var earningInput = document.getElementById("earning");

document.body.onclick = function(event) {
    var target = event.target;
    if (target.className !== "company") {
        if (target.parentElement.className === "company")
            target = target.parentElement;
        else return;
    }
    if(target.parentElement.lastElementChild.style.display === "") {
        target.parentElement.lastElementChild.style.display = "none";
    }
    else target.parentElement.lastElementChild.style.display = "";
};

onload = function() {
    postorderCountTotalEarning(document.getElementById("tree").firstElementChild);
};

function postorderCountTotalEarning(ul) {
    if (ul === null)
        return;

    var i;
    for (i = 0; i < ul.children.length; i++) {
        postorderCountTotalEarning(ul.children[i].lastElementChild);
    }
    var sum = 0;
    for (i = 0; i < ul.children.length; i++) {
        var earningSpan = ul.children[i].firstElementChild.getElementsByClassName("total-earning")[0];
        var earning;
        if (earningSpan !== undefined)
            earning = parseInt(earningSpan.innerText);
        else {
            earningSpan = ul.children[i].firstElementChild.getElementsByClassName("earning")[0];
            earning = parseInt(earningSpan.innerText);
        }
        sum += earning;
    }
    if (ul.children.length !== 0 && ul.parentElement.firstElementChild !== null)
        displayTotalEarning(ul.parentElement.firstElementChild, sum);
}

function displayTotalEarning(companyElement, childrenEarning) {
    if (companyElement !== null && companyElement.tagName !== "UL") {
        var totalEarningElements = companyElement.getElementsByClassName("total-earning");
        var totalEarning = parseInt(companyElement.getElementsByClassName("earning")[0].innerText) + childrenEarning;

        if (totalEarningElements.length > 0) {
            totalEarningElements[0].innerText = totalEarning;
        } else {
            companyElement.innerHTML += ' | <span class="total-earning">' + (totalEarning) + '</span>K$';
        }
    }
}

function openEditWindow(companyElement) {
    editedCompanyElement = companyElement;
    if (companyElement !== undefined) {
        nameInput.value = companyElement.getElementsByClassName("name")[0].innerText;
        var parent = companyElement.parentElement.parentElement.parentElement;
        if (parent.id !== "tree")
            parentInput.value = parent.firstElementChild.getElementsByClassName("name")[0].innerText;
        else
            parentInput.value = "";
        earningInput.value = companyElement.getElementsByClassName("earning")[0].innerText;
    } else {
        nameInput.value = "";
        parentInput.value = "";
        earningInput.value = "0";
    }
    editWindow.style.display = "";
}

function onDeleteClicked(companyElement) {
    var sure = confirm("Are you sure to delete company \"" +
        companyElement.getElementsByClassName("name")[0].innerText + "\" from base? " +
        "If it has any children - they would become orphans!");
    if (sure) {
        var companyName = companyElement.getElementsByClassName("name")[0].innerText;
        window.location = "delete_company?companyName=" + companyName;
    }
}

function onAddChildClicked(companyName) {
    nameInput.value = "";
    parentInput.value = companyName;
    earningInput.value = "0";
    editWindow.style.display = "";
}

function onSave() {
    var previousName = editedCompanyElement !== undefined ?
        editedCompanyElement.getElementsByClassName("name")[0].innerText : undefined;
    var companyName = nameInput.value;
    var parentName = parentInput.value;
    var earnings = parseInt(earningInput.value);
    if (isNaN(earnings)) {
        alert("Earnings should be an integer number");
        return;
    }
    //check if user isn't trying to make company a child of itself
    if (parentName === companyName) {
        alert("Company can't become a child of itself");
        return;
    }
    //check if user isn't trying to set company's child as its parent
    if (editedCompanyElement !== undefined &&
        getParent(parentName) === previousName) {
        alert("Company can't become a child of its child");
        return;
    }
    //check if user has sanity
    if (parentName === previousName) {
        alert("Don't do it this way");
        return;
    }
    //check if specified parent exists
    if (parentName !== "" && !companyWithNameExists(parentName)) {
        alert("Such Parent Company does not exist");
        return;
    }
    //check if specified company name exists
    if (editedCompanyElement !== undefined &&
        previousName !== companyName && companyWithNameExists(companyName)) {
        alert("Company with such name already exists");
        return;
    }

    if (editedCompanyElement !== undefined) {
        window.location = "update_company?previousName=" + previousName + "&companyName=" + companyName +
            "&parentName=" + parentName + "&earnings=" + earnings;
    } else {
        window.location = "add_company?companyName=" + companyName +
            "&parentName=" + parentName + "&earnings=" + earnings;
    }

    editedCompanyElement = undefined;
    editWindow.style.display = "none";
}

function companyWithNameExists(companyName) {
    var recursiveSearch = function (element) {
        for (var i = 0; i < element.children.length; i++) {
            if (element.children[i].firstElementChild.firstElementChild.innerText === companyName) {
                return true;
            }
            if (recursiveSearch(element.children[i].lastElementChild)) {
                return true;
            }
        }
        return false;
    };
    return recursiveSearch(document.getElementById("tree").firstElementChild);
}

function getParent(companyName) {
    var recursiveSearch = function (element) {
        for (var i = 0; i < element.children.length; i++) {
            if (element.children[i].firstElementChild.firstElementChild.innerText === companyName) {
                if (element.parentElement.tagName !== "DIV") {
                    return element.parentElement.firstElementChild.firstElementChild.innerText;
                }
            }
            var recursiveResult = recursiveSearch(element.children[i].lastElementChild);
            if (recursiveResult !== null) {
                return recursiveResult;
            }
        }
        return null;
    };
    return recursiveSearch(document.getElementById("tree").firstElementChild);
}