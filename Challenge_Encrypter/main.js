principalLetters = ["a", "e", "i", "o", "u"];
changeLetters = ["ai", "enter", "imes", "ober", "ufat"];

function serchArrays(letter, array) {
  for (var i = 0; i < array.length; i++) {
    if (letter === array[i]) {
      return [true, i];
    }
  }
  return false;
}

function encrypt(text) {
  var encryptedText = "";
  var position;

  for (var i = 0; i < text.length; i++) {
    position = serchArrays(text[i], principalLetters);
    if (position[0]) {
      encryptedText += changeLetters[position[1]];
    } else {
      encryptedText += text[i];
    }
  }

  document.getElementById("workingText").value = encryptedText;
}

function decrypt(text) {
  var i = 0; //contador
  var decryptedText = "";
  var position;

  while (i < text.length) {
    position = serchArrays(text[i], principalLetters);
    if (position[0]) {
      decryptedText += principalLetters[position[1]];
      i += changeLetters[position[1]].length;
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
