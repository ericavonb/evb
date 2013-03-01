$("#homepage_nav_1").click(function () {
    $(".contact").fadeOut("slow")
    $(".name").animate({top:'50px', left: '0px'}, 1000, function() {
    // Animation complete.
  });
    $(".about").fadeIn("slow");
      });
$("#homepage_nav_5").click(function () {
 $(".about").fadeOut("slow")    
$(".name").animate({top:'50px', left: '0px'}, 1000, function() {
    // Animation complete.
  });
            $(".contact").fadeIn("slow");

      });
$(".name").click(function () {
     $(".about").fadeOut("slow")  
     $(".contact").fadeOut("slow");  
     $(".name").animate({top:'175px', left: '425px'}, 1000, function() {
    // Animation complete.
  });
            

      });
