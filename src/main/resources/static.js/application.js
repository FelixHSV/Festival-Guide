

//!!!!!!!!!!
//Hier war ich bisher etwas erfolglos. Bitte mal alles mit Javascript einfügen. Bis jetzt ist das nur der übernommene Code von Olli
//!!!!!!!


$(function() {

    // demo: change background color of input fields on mouseover
    $('input, select').mouseover(function() {
        $(this).css('backgroundColor', 'yellow');
    }).mouseout(function() {
        $(this).css('backgroundColor', '');
    });

    // display help when the search form input is focused
    var $help = $('#searchForm .help');
    $help.hide();
    $('#searchForm input').focus(function() {
       $help.show();
    }).blur(function() {
       $help.hide();
    });

    // slide up and down the change form
    var $changeForm = $('#changeForm');
    $changeForm.hide();
    $('#changeButton').click(function() {
       $changeForm.slideToggle();
    });

    // show confirm dialog before deleting a journal
    $('#deleteForm').submit(function() {
        return confirm('Wirklich löschen?');
    });

    // perform journal search using Ajax
    var $searchInput = $('#searchForm input');
    var $searchResult = $('#searchResult');
    $('#searchForm').submit(function() {
        $.getJSON('/journals/search', { search: $searchInput.val() }, function(results) {
            $searchResult.children().remove();
            results.forEach(function(result) {
                var $link = $('<a>').attr('href', result.link).text(result.name);
                var $item = $('<li>').append($link);
                $searchResult.append($item);
            });
        });
        return false;
    });

});
