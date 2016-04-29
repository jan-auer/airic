(function ($) {

    $(document).on('click', '.btn-message-remove', function (e) {
        $(this).closest('.message').fadeOut();
        e.stopImmediatePropagation();
    });

})(jQuery);
