const principalLetters = ["a", "e", "i", "o", "u"];
const changeLetters = ["ai", "enter", "imes", "ober", "ufat"];

function serchArrays(letter, array) {
  for (let i = 0; i < array.length; i++) {
    if (letter === array[i]) {
      return [true, i];
    }
  }
  return false;
}

function encrypt(text) {
  let encryptedText = "";
  let position;

  for (let i = 0; i < text.length; i++) {
    position = serchArrays(text[i], principalLetters);
    if (position[0]) {
      encryptedText += changeLetters[position[1]];
    } else {
      encryptedText += text[i];
    }
  }

  document.getElementById("workingText").textContent = encryptedText;
}

function decrypt(text) {
  let i = 0; //contador
  let decryptedText = "";
  let position;

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

  document.getElementById("workingText").textContent = decryptedText;
}

function encryptFunction() {
  text = document.getElementById("text").textContent;
  document.getElementById("text").innerText = "";
  if (text != "") {
    document.getElementById("noText").style.display = "none";
    document.getElementById("withText").style.display = "flex";
    encrypt(text);
  } else {
    alert("Debe agregar algun texto");
  }
}

function decryptFunction() {
  text = document.getElementById("text").textContent;
  document.getElementById("text").innerText = "";
  if (text != "") {
    document.getElementById("noText").style.display = "none";
    document.getElementById("withText").style.display = "flex";
    decrypt(text);
  } else {
    alert("Debe agregar algun texto");
  }
}

function copyText() {
  var copyText = document.getElementById("workingText");

  navigator.clipboard.writeText(copyText.textContent);
  alert("Texto Copiado")
}
