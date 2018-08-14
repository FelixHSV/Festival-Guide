$(function() {

    //Ein- und Ausklappen der Festivalbearbeitung für Administratoren
    var $changeDivFestival = $('#changeDivFestival');
    $changeDivFestival.hide();
    $('#changeButtonFestival').click(function() {
       $changeDivFestival.slideToggle();
    });

    //Bestätigungsaufforderung beim Löschen eines Festivals
    $('#deleteFormFestival').submit(function() {
        return confirm('Möchten Sie das Festival wirklich löschen?');
    });

    //Ein- und Ausklappen der Bandbearbeitung für Administratoren
    var $changeDivBand = $('#changeDivBand');
    $changeDivBand.hide();
    $('#changeButtonBand').click(function() {
        $changeDivBand.slideToggle();
    });

    //Bestätigungsaufforderung beim Löschen einer Band
    $('#deleteFormBand').submit(function() {
        return confirm('Möchten Sie die Band wirklich löschen?');
    });

});
