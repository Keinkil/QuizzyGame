$(function() {
  var passwordfield = $("input[name=password]");
  var textfield = $("input[name=user]");
  $('button[type="submit"]').click(function(e) {
    e.preventDefault();
    //little validation just to check username
    if (textfield.val() == "admin" && passwordfield.val() == "mah") {
      //$("body").scrollTo("#output");
      $("#output").addClass("alert alert-success animated fadeInUp").html("Welcome back " + "<span style='text-transform:uppercase'>" + textfield.val() + "</span>");
      $("#output").removeClass(' alert-danger');
      $("input").css({
        "height": "0",
        "padding": "0",
        "margin": "0",
        "opacity": "0"
      });

      //change button text
      $('button[type="submit"]').html("continue")
        .removeClass("btn-info")
        .addClass("btn-default").click(function() {
          $("input").css({
            "height": "auto",
            "padding": "10px",
            "opacity": "1"
          }).val("");
        });

      $('button[type="submit"]').click(function() {
        window.location.replace("basic.html")
      });
    } else if (textfield.val == "" || passwordfield.val == "") {
      //remove success mesage replaced with error message
      $("#output").removeClass(' alert alert-success');
      $("#output").addClass("alert alert-danger animated fadeInUp").html("sorry enter a username and password");
    } else if (textfield.val != "admin" || passwordfield.val != "mah") {
      //remove success mesage replaced with error message
      $("#output").removeClass(' alert alert-success');
      $("#output").addClass("alert alert-danger animated fadeInUp").html("sorry, the username or password is incorrect");
    }
    //console.log(textfield.val());

  });
});
