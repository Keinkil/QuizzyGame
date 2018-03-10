//////////////////////////////////////
//////// UI AND FUNCTIONALITY ////////
//////////////////////////////////////

function removeCategory(category){
  var categoryName = $(category).text();
  //TODO Call API with name of category in order to remove it
  alert("Remove category: " + categoryName);

  //Refresh list after call
  refreshCategoryList();
};

function editCategory(category){
  $("#dialogModal").modal();
  var categoryName = $(category).text();
  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(categoryName);

  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label for="categoryNameEdit">Rename category:</label>'
    + '<br><input type="text" class="form-control" id="categoryNameEdit" value="' + categoryName + '">'
  );

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="postEditCategory(\'' + categoryName + '\')">Ok</button>'
  );

}

function postEditCategory(editCategory){
  var newCategoryName = $("#categoryNameEdit").val();
  //TODO AJAX CALL
  alert("Ajax call received -  OldName: " + editCategory + " NewName: " + newCategoryName);

  //TODO Refresh list if successful call was made to API
  refreshCategoryList();
}

function addNewCategory(){
  var categoryName = $('#categoryName').val();
  var total = $(".contentMain li").length;

  //TODO Call create new category (API)
  //Use categoryName when you call API
  //If successful do below
  $(".contentMain" ).append(
      '<li class="list-group-item" id="category' + total + '">' + categoryName
    + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + total + ')"></span>'
    + '<span class="glyphicon glyphicon-pencil" onclick="editCategory  (category' + total + ')"></span>'
    + '</li>'
  );
  $("#categoryName").val('');
}

function refreshCategoryList(){
  //TODO Get categoryList from API, replace dummy-data below
  var categoryArr = ["Dogs", "Cats", "Birds", "Cars", "Sausages", "Icecream"];

  $( ".contentMain").empty();
  $( ".contentMain" ).append(
      "<h2>Category</h2>"
    + '<label for="categoryName">Enter category name:</label><br><input type="text" class="form-control" id="categoryName">'
    + '<button type="button" class="btn btn-success" onclick="addNewCategory()" id="buttonAddCategory">Add new category</button>'
  );

  $( ".contentMain" ).append('<ul class="list-group">');

  for(var i = 0; i<categoryArr.length; i++){
    $( ".contentMain" )
      .append(
        '<li class="list-group-item" id="category' + i + '">' + categoryArr[i]
      + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + i + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editCategory(category' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}

$(window).on('load', function(){
  $("#categoriesMenu").on('click', function() {
    refreshCategoryList();
  });

  $("#questionsMenu").on('click', function() {
    $( ".contentMain").empty();
    $( ".contentMain" ).append( "<h2>Questions</h2><p>Gj, questions!!");
  });

  $("#answersMenu").on('click', function() {
    $( ".contentMain").empty();
    $( ".contentMain" ).append( "<h2>Answers</h2><p>Gj, answers!!!");
  });

  $('a').click(function(){
      $('.active').removeClass('active');
      $(this).addClass('active');
  });
});
