function Somme(){
  let table = document.getElementById("ma-table");
  let tbody = table.getElementsByTagName("tbody")[0];
  let colonne = tbody.getElementsByTagName("td");
  let somme = 0;

  for (let i = 0; i < colonne.length; i++) {
    if (colonne[i].id === "montant") {
      somme += parseInt(colonne[i].innerHTML);
    }
  }

  let sommeCell = document.getElementById("total");
  sommeCell.innerHTML = somme;
}
