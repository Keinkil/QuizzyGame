//////////////////////////////////////
//////// UI AND FUNCTIONALITY ////////
//////////////////////////////////////

////////////////////////////
//////// CATEGORIES ////////
////////////////////////////
function removeCategory(category){
  var categoryName = $(category).text();
  deleteCategory(categoryName);
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
    + 'id="okDialogButton" onclick="sendEditCategory(\'' + categoryName + '\')">Ok</button>'
  );
}

function sendEditCategory(editCategory){
  var newCategoryName = $("#categoryNameEdit").val();
  if(newCategoryName != ""){
    putChangedCategory(editCategory, newCategoryName);
    refreshCategoryList();
  }else{
    alert("Didn't save changes. You must enter at least one character.")
  }
}

function addNewCategory(){
  var categoryName = $('#categoryName').val();

  if(categoryName != ""){
    postNewCategory(categoryName);
  }else{
    alert("You must enter at least one character.")
  }
}

function showNewlyAddedCategory(){
  var total = $(".contentMain li").length;
 var categoryName = $('#categoryName').val();


  $(".contentMain" ).append(
      '<li class="list-group-item" id="category' + total + '">' + categoryName
    + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + total + ')"></span>'
    + '<span class="glyphicon glyphicon-pencil" onclick="editCategory  (category' + total + ')"></span>'
    + '</li>'
  );
  $("#categoryName").val('');
}

function refreshCategoryList(){
  $( ".contentMainHeader").empty();
  $( ".contentMain").empty();
  $( ".contentMainHeader" ).append(
      '<h2>Category</h2>'
    + '<hr>'
    + '<div class="titelCreate">'
    + '<h4>Create new category</h4>'
    + '<label for="categoryName">Enter category name:</label><br><input type="text" class="form-control" id="categoryName">'
    + '<button type="button" class="btn btn-success" onclick="addNewCategory()" id="buttonAddCategory">Add new category</button>'
    + '</div>'
  );
  getCategories(1);
}

function updateCategoryList(res){
  $( ".contentMain" ).append('<ul class="list-group">');
      for(var i = 0; i<res.length; i++){
    $( ".contentMain" )
      .append(
        '<li class="list-group-item" id="category'+ i + '">' + res[i]
      + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + i + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editCategory(category' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}
////////////////////////////
//////// CATEGORIES ////////
////////     END    ////////
////////////////////////////

///////////////////////////
//////// QUESTIONS ////////
///////////////////////////
function addNewQuestion(){
  //TODO values from below need to be sent to API as well! Look at comment below!
  var question = $("#createNewQuestion").val();
  var answer = $("#createNewQuestionAnswer").val();
  var categories = $("#multiBoxCategory").val();
  if(question != "" && answer != "" && categories != ""){
    var files  = document.getElementById('my-file-selector');
    var file = files.files[0];
    var reader  = new FileReader();
    if(typeof files.files[0] === 'undefined'){
      //TODO post to API (without file)
          postNewQuestion(categories, question);
      //TODO If successful refresh UI
      refreshCategoryPickerHeader();
    }else{
      reader.readAsDataURL(file);
      reader.onload = function () {
        console.log(reader.result);//this is the base64 encdoded file as string
        //TODO post to API with file
        //TODO If successful refresh UI
        refreshCategoryPickerHeader();
      };
    }
  }else{
    alert("Didn't create a new question. Please enter question, answer and choose at least one category.")
  }
}

function removeQuestion(question){
  var questionName = $(question).text();
  var questionID = $(question).val();
  //TODO Call API with questionID and remove it
  deleteQuestion(questionID);
  refreshDeletedQuestionList();
  alert("Removed question: " + questionName + " questionID: " + questionID);


};

function refreshDeletedQuestionList(){
   //Refresh list after call
  var selectedCategory = $('#selectCategoryHeader').val();
  getQuestions(selectedCategory);
};

function editQuestion(question){
  $("#dialogModal").modal();
  var questionName = $(question).text();
  var questionID = $(question).val();
  //TODO API, get all selected categories with questionID, store in prevSelected
  var prevSelected = ["Sausages", "Dogs"];

  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(questionName);
  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label>Rename question:</label>'
    + '<br><input type="text" class="form-control" id="questionNameEdit" value="' + questionName + '">'
    + '<br><br><label>Change categories:</label><br>'
    + '<select multiple="" id="multiBoxCategoryEditQuestion" class="form-control select-checkbox" size="3">'
    + '</select><br>'
  );

  $("#multiBoxCategory option").each(function(){
      var add = $(this).val();
      $( "#multiBoxCategoryEditQuestion").append(
          '<option>' + add + '</option>'
      );
  });

  $('#multiBoxCategoryEditQuestion').val(prevSelected).trigger('chosen:updated');

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="sendEditQuestion(\'' + questionID + '\')">Ok</button>'
  );
}

function sendEditQuestion(questionID){
  var selectedCategories = $('#multiBoxCategoryEditQuestion').val();
  var newQuestion = $('#questionNameEdit').val();
  if(selectedCategories != "" && newQuestion != ""){
    //TODO AJAX CALL
    putChangeQuestion(questionID, newQuestion, selectedCategories);
    alert("Ajax call - questionID: " + questionID + " newQuestion: " + newQuestion + " selectedCategories: " + selectedCategories);
  }else{
    alert("Didn't save changes. Please enter at least one character and choose at least one category.")
  }
}

function refreshCategoryPickerHeader(){
  $( ".contentMain").empty();
  $( ".contentMainHeader").empty();
  $( "#selectCategoryHeader").empty();
  $( ".contentMainHeader" ).append(
      '<h2>Question</h2>'
    + '<hr>'
    + '<h4>Create new question</h4>'
    + '<div class="titelCreate">'
    + '<label>Enter question:</label><br>'
    + '<input type="text" class="form-control" id="createNewQuestion" value=""><br>'
    + '<label>Enter correct answer:</label><br>'
    + '<input type="text" class="form-control" id="createNewQuestionAnswer" value=""><br>'

    + '<label>Choose one or more categories:</label><br>'
    + '<select multiple="" id="multiBoxCategory" class="form-control select-checkbox" size="3">'
    + '</select><br>'

    + '<label>Upload multimedia (optional):</label><br>'
    + '<label class="btn btn-default" for="my-file-selector">'
    + '<input id="my-file-selector" type="file" style="display:none" '
    + 'onchange="$(\'#upload-file-info\').html(this.files[0].name)">'
    + 'Browse'
    + '</label>'
    + '<span class="label label-info" id="upload-file-info"></span><br><br>'

    + '<button type="button" class="btn btn-success" onclick="addNewQuestion()" id="buttonAddNewQuestion">Add new question</button>'

    + '</div>'
    + '<hr>'
    + '<label for="selectCategoryHeader">Fetch questions from:</label><br>'
    + '<select class="form-control" id="selectCategoryHeader">'
    + '</div>'
  );
getCategories(2);
};

function updateCategoryListInQuestions(result){
  //Adds empty string so nothing is preselectedS
  $( "#selectCategoryHeader").append(
      '<option></option>'
  );
  for(var i = 0; i<result.length; i++){
    $( "#selectCategoryHeader").append(
        '<option>' + result[i] + '</option>'
    );
    $( "#multiBoxCategory").append(
        '<option>' + result[i] + '</option>'
    );
  }

  $('#selectCategoryHeader').change(function(){
    var categoryname = $(this).val();
    if(categoryname != ""){
      getQuestions(categoryname);
    }else {
      $(".contentMain").empty();
    }
  });
}

function showFilteredQuestions(questionsArr){
  $(".contentMain").empty();
  $(".contentMain").append('<ul class="list-group">');
  for(var i = 0; i<questionsArr.length; i++){
    console.log(questionsArr[i]);
    $( ".contentMain" )
      .append(
        '<li class="list-group-item" value="' + questionsArr[i].id + '" id="question' + questionsArr[i].id + '">' + questionsArr[i].question
      + '<span class="glyphicon glyphicon-trash"  onclick="removeQuestion(question' + questionsArr[i].id + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editQuestion(question' + questionsArr[i].id + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}


///////////////////////////
//////// QUESTIONS ////////
////////    END    ////////
///////////////////////////


///////////////////////////
////////  ANSWERS  ////////
///////////////////////////
function refreshAnswers(questionsArr){
  $( ".contentMainHeader").empty();
  $( ".contentMain").empty();
  $( ".contentMainHeader" ).append(
      '<h2>Answer</h2>'
    + '<hr>'
    + '<div class="titelCreate">'
    + '<h4>Create new answer</h4>'
    + '<label for="selectQuestion">Fetch answers to:</label><br>'
    + '<select class="form-control" id="selectQuestion"></select><br>'
    + '</div>'
  );

  $( "#selectQuestion").append(
      '<option></option>'
  );
  for(var i = 0; i<questionsArr.length; i++){
    $( "#selectQuestion").append(
        '<option value="' + questionsArr[i].id + '">' + questionsArr[i].question + '</option>'
    );
  }

  $('#selectQuestion').change(function(){
    var questionID = $(this).val();
    if(questionID != ""){
      listAnswers(questionID);
    }
  });
}

function listAnswers(questionID){

  getAllAnswersBasedOnQuestion(questionID);
}

function displayAnswersBasedOnQuesiton(answersJson){
  $(".contentMain").empty();
  $(".contentMain").append(
      '<ul class="list-group">'
    + '<label for="answerName">Enter answer:</label><br><input type="text" class="form-control" id="answerName">'
    + '<button type="button" class="btn btn-success" onclick="addNewAnswer()" id="buttonAddAnswer">Add new answer</button>'
  );

  for(var i = 0; i<answersJson.length; i++){
    var styleRowLi = "";
    var styleRowEdit = "";
    var trashcan = "";
    if(answersJson[i].correctAnswer){
      styleRowLi = 'style="background-color:green;"';
      styleRowEdit = 'style="margin-right:24px;"';
    }else{
      trashcan = '<span class="glyphicon glyphicon-trash"  onclick="removeAnswer(answer' + i + ')"></span>';
    }

    $(".contentMain").append(
        '<li class="list-group-item" ' + styleRowLi + ' value="' + answersJson[i].id + '" id="answer' + i + '">' + answersJson[i].answer
      + trashcan
      + '<span class="glyphicon glyphicon-pencil" ' + styleRowEdit + ' onclick="editAnswer(answer' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}

function editAnswer(answer){
  $("#dialogModal").modal();
  var answerName = $(answer).text();
  var answerID = $(answer).val();
  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(answerName);
  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label>Change answer:</label>'
    + '<br><input type="text" class="form-control" id="answerNameEdit" value="' + answerName + '">'
  );

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="sendEditAnswer(\'' + answerID + '\')">Ok</button>'
  );
}

function sendEditAnswer(answerID){
  var newAnswerName = $("#answerNameEdit").val();
  if(newAnswerName != ""){
    putAnswer(answerID, newAnswerName);
  }else{
    alert("Didn't save changes, answer needs to be at least one character!")
  }
}

function addNewAnswer(){
  var questionID = $('#selectQuestion').val();
  var answerName = $('#answerName').val();
  var total = $(".contentMain li").length;

  if(answerName != "" && questionID != ""){
    postNewAnswer(questionID, answerName)
    $("#answerName").val('');
  }else {
    alert("Please enter your answer and choose a question!");
  }
}


function removeAnswer(answer){
  var answerID = $(answer).val();
  deleteAnswer(answerID);
};
///////////////////////////
////////  ANSWERS  ////////
////////    END    ////////
///////////////////////////

//////////////////////////
////////   MAIN   ////////
//////////////////////////
$(window).on('load', function(){
  $("#categoriesMenu").on('click', function() {
    refreshCategoryList();
  });

  $("#questionsMenu").on('click', function() {
    refreshCategoryPickerHeader();
  });

  $("#answersMenu").on('click', function() {
    $( ".contentMain").empty();
    getAllQuestions();
  });

  $('a').click(function(){
      $('.active').removeClass('active');
      $(this).addClass('active');
  });
});
//////////////////////////
////////   MAIN   ////////
////////   END    ////////
//////////////////////////
