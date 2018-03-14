function deleteCategory() {
  let dropdown = $("#catDrop");
  var choice = "";
  dropdown.hidden();
  dropdown.empty();

  dropdown.append('<option selected="true" disabled>Select Category</option>');
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category/ ",
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    $.each(res, function(key, entry) {
      dropdown.append($('<option></option>').attr('value', entry).text(entry));
    })
    console.log("Hej Lykke");
  })
};

function removeCategory() {
  var idVal = document.getElementById("catDrop").value;
  $.ajax({
    type: "DELETE",
    url: "http://localhost:5000/api/1/category/" + idVal,
    headers: {
      "Accept": "options"
    }
  }).done(function(res) {
    console.log(res + "DELETE ass");
  })
};

function showCategory() {
  let dropdown = $("#catDrop");
  var choice = "";

  dropdown.empty();
  dropdown.append('<option selected="true" disabled>Select Category</option>');
  var html = "";
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category/ ",
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    html = "";
    list = $('#Categorys');
    list.empty();
    for (i = 0; i < res.length; i++) {

      html += '<li id=Categorys>' + res[i] + '</li> <br>';
    }
    list.append(html);
    console.log(res);
    $.each(res, function(key, entry) {
      dropdown.append($('<option></option>').attr('value', entry).text(entry));
      var x = document.getElementById("editForm");
      if (x.style.display === "hidden" || x.style.display === "block") {
        x.style.display = "none";
      } else {
        x.style.display = "block";
      }

    })
  })
};

function editCategory() {
  var laj = "oh";
  var idVal = document.getElementById("catDrop").value;

  var data = {};
  data.name = document.getElementById("catDrop").value;
  data.changeCat = $('#changeCategory input[name=changeCat]').val();

  $.ajax({
    type: "PUT",
    url: "http://localhost:5000/api/1/category/",
    data: JSON.stringify(data),
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    console.log(res + "put?!");
  })
};

// function postNewCategory(categoryName){          //Såhär kan addCategory() (notera namnbytet på metoden) implementeras. Notera parametern också.
//   $.ajax({
//     type: "POST",
//     url: "http://localhost:5000/api/1/category/" + categoryName,
//     headers: {
//       "Accept": "application/json"
//     },
//     success: function(){     //Success sker när ett anrop går igenom.
//       return true;           //Man kan beskriva vad man vill ska hända vid en success
//     },
//     error: function(XMLHttpRequest, textStatus, errorThrown) {     //Error sker om anropet inte går igenom
//       alert("Status: " + textStatus + " Error: " + errorThrown);   //I detta fall sker då en alert
//       return false;
//     }
//   });
// }

function addCategory() {
  var idVal = document.getElementById("inputNameCat").value;

  $.ajax({
    type: "POST",
    url: "http://localhost:5000/api/1/category/" + idVal,
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    showCategory();
    console.log("post?!");
  })
};

function tryToAdd() {
  var x = document.getElementById("testform");
  if (x.style.display === "hidden" || x.style.display === "block") {
    x.style.display = "none";
  } else {
    x.style.display = "block";
  }
};

function postQ() {
  var idVal = $('#newQuestion input[name=name]').val();
  var data = {};
  //data.name = $('#newQuestion input[name=name]').val();//-->
  data.name = document.getElementById("catDrop").value;
  data.question = $('#newQuestion input[name=question]').val();
  $.ajax({
    type: "POST",
    url: "http://localhost:5000/api/1/category/ ",
    data: JSON.stringify(data),
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
      console.log(res + data.name + data.question);
    }
  )
};

function showQ() {
  var idVal = document.getElementById("catDrop").value;
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category/ " + idVal,
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    console.log(res + "mannen");
  })
};
