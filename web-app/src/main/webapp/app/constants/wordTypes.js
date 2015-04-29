var models = angular.module( 'tamilapp.constants' );
models.factory( 'wordTypes', function() {
var mapvalue = new Object();
mapvalue['TENSE'] = 'காலம்';
mapvalue['EAN'] = 'தன்மை-ஒருமை';
mapvalue['OAM'] = 'தன்மை-பன்மை';
mapvalue['AAY'] = 'முன்னிலை-ஒருமை';
mapvalue['EER'] = 'முன்னிலை-பன்மை';
mapvalue['THU'] = 'படர்க்கை-ஒன்றன்பால்';
mapvalue['A'] = 'படர்க்கை-பலவின்பால்';
mapvalue['AAN'] = 'படர்க்கை-ஆண்பால்';
mapvalue['AALH'] = 'படர்க்கை-பெண்பால்';
mapvalue['AAR'] = 'படர்க்கை-பல்ர்பால்-ஒருமை';
mapvalue['AR'] = 'படர்க்கை-பல்ர்பால்-பன்மை';
mapvalue['PAAL'] = 'பால்';
mapvalue['PRESENT'] = 'நிகழ்காலம்';
mapvalue['PAST'] = 'இறந்தகாலம்';
mapvalue['FUTURE'] = 'எதிர்காலம்';
mapvalue['MUTTU'] = 'முற்றுக்காலம்';
mapvalue['THODAR'] = 'தொடர்காலம்';
mapvalue['TRUE'] = '-ஆம்-';
mapvalue['FALSE'] = '-இல்லை-';
mapvalue['ROOT'] = 'வேர்ச்சொல்';
mapvalue['IDAICHCHOL'] = 'இடைச்சொல்';
mapvalue['PEYARCHCHOL'] = 'பெயர்ச்சொல்';
mapvalue['EERRUKEDDATHU'] = 'ஈறுகெட்டது';
mapvalue['THANI'] = 'தனி';
mapvalue['KADDALHAI'] = 'கட்டளை';
mapvalue['PEYARECHCHAM'] = 'பெயரெச்சம்';
mapvalue['VINAIYECHCHAM'] = 'வினையெச்சம்';
mapvalue['ETHIRMARRAI'] = 'எதிர்மறை';
mapvalue['THALAI'] = 'தலை';
mapvalue['MUDI'] = 'முடி';
mapvalue['UYARTHINHAI'] = 'உயர்திணை';

var wordTypeObj = new Object();
wordTypeObj ['map'] = mapvalue;
wordTypeObj ['toTamil'] = function (w) {
    var va = this.map[w.toUpperCase()];
    if (va != null) {
        return va;
    } else {
        return w;
    }
}  
return wordTypeObj;
} );