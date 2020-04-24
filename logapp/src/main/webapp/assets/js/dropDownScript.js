$(document).ready(function() {
    $('.nav-item>a').click(function() {
        $('.nav-item>a').removeClass('active');
        if ($(this).next('.dropMenu').css("display") == "none") {
            $('.dropMenu').hide('normal');
            $(this).next('.dropMenu').toggle('normal');
            $('.nav-item>a').removeClass('active');
            $(this).toggleClass('active');
        } else $('.dropMenu').hide('normal');
        return false;
    });
});