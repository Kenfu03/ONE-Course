principalLetters = ["a", "e", "i", "o", "u"];
changeLetters = ["ai", "enter", "imes", "ober", "ufat"];

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

  document.getElementById("workingText").value = encryptedText;
}

function decrypt(text) {
  var i = 0; //contador
  decryptedText = "";

  while (i < text.length) {
    if (serchArrays(text[i], principalLetters)) {
      for (var j = 0; j < principalLetters.length; j++) {
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

  document.getElementById("workingText").value = decryptedText;
}

function encryptFunction() {
  text = document.getElementById("text").textContent;
  document.getElementById("text").innerText = "";
  document.getElementById("noText").style.display = "none";
  document.getElementById("withText").style.display = "flex";
  encrypt(text);
}

function decryptFunction() {
  text = document.getElementById("text").textContent;
  document.getElementById("text").innerText = "";
  document.getElementById("noText").style.display = "none";
  document.getElementById("withText").style.display = "flex";
  decrypt(text);
}

function copyText() {
  var copyText = document.getElementById("workingText");

  copyText.select();
  copyText.setSelectionRange(0, 99999);

  navigator.clipboard.writeText(copyText.value);
}
