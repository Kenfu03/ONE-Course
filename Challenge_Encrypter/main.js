principalLetters = ["a", "e", "i", "o", "u"];
changeLetters = ["ai", "enter", "imes", "ober", "ufat"];

var decryptedText = "hola como esta";
var encryptedText = "hoberlai cobermober enterstai";

function serchArrays(letter, array) {
  for (var i = 0; i < array.length; i++) {
    if (letter === array[i]) {
      return true;
    }
  }
  return false;
}

function encrypt(text) {
  var encryptedText = "";

  for (var i = 0; i < text.length; i++) {
    if (serchArrays(text[i], principalLetters)) {
      for (var j = 0; j < principalLetters.length; j++)
        if (text[i] === principalLetters[j]) {
          encryptedText += changeLetters[j];
        }
    } else {
      encryptedText += text[i];
    }
  }

  console.log("Encrypted");
  console.log(text);
  console.log(encryptedText);
}

function decrypt(text) {
  var i = 0; //contador
  decryptedText = "";

  while (i < text.length) {
    if (serchArrays(text[i], principalLetters)) {
      for (var j = 0; j < principalLetters.length; j++){
        if (text[i] === principalLetters[j]) {
          decryptedText += principalLetters[j];
          i += changeLetters[j].length;
        }
      }
    } else {
      decryptedText += text[i];
      i++;
    }
  }

  console.log("Decrypted");
  console.log(text);
  console.log(decryptedText);
}

encrypt(decryptedText);
decrypt(encryptedText);
