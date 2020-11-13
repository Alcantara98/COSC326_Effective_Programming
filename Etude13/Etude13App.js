//JavaScript

/*
        @param numx the number to convert
        @param basex numx gets converted to this base
        @return the converted number
        This function takes a number and converts it to the given base.
        */
function baseConvert(numx, basex) {
  var inverted = [];
  var end = false;
  var baseString = basex.toString();

  // Converts the input max value to the input base,
  // however the method used gives us the number in that
  // base, but inverted.
  while (!end) {
    if (numx >= basex) {
      var result = numx % basex;
      inverted.push(result.toString());
      numx = parseInt(numx / basex);
    } else {
      inverted.push(numx.toString());
      end = true;
    }
  }

  var converted = [];
  // Inverts the inverted number.
  for (var i = inverted.length - 1; i >= 0; i--) {
    var result = inverted[i];
    if (result.length < baseString.length) {
      var difference = baseString.length - result.length;
      var temporary = "";
      for (var j = 0; j < difference; j++) {
        temporary += "0";
      }
      temporary += result;
      result = temporary;
    }
    converted.push(result.toString());
  }
  return converted;
}

/*
    Check whether argument number has repeated digits.

    @param numx 
    */
function repeatedNumCheck(numx) {
  var repeated = false;
  for (var i = 0; i < numx.length; i++) {
    for (var j = 0; j < numx.length; j++) {
      if (j != i && numx[i] == numx[j]) {
        repeated = true;
        break;
      }
    }
    if (repeated) {
      return true;
    }
  }

  return false;
}

function partA() {
  var ab = parseInt(document.getElementById("ab").value);
  var an = parseInt(document.getElementById("an").value);
  var abString = ab.toString();
  if (ab > 1 && an > ab) {
    var maxBaseB = baseConvert(an, ab);
    var maxSameNumbers = "";
    var maxBaseBString = "";
    var largestNumber = maxBaseB[0];
    /*
            Takes the left most number of the max value in provided base
            and creates a number of the same length using this number only.
            */
    for (var i = 0; i < maxBaseB.length; i++) {
      maxSameNumbers += largestNumber;
      maxBaseBString += maxBaseB[i];
    }
    /*
            If the number is then larger than max int value, then the number 
            one less than the repeated digit will be the largest value
            that has all repeated digits for the base that is less or 
            equal to the max int value provided.
            */
    if (maxSameNumbers >= maxBaseBString) {
      var temporary = "";
      var maxBaseBlength = maxBaseB.length;
      largestNumber = parseInt(largestNumber) - 1;

      // If 0, this means we have to decrease length by one, but still use
      // repeated 1s.
      if (largestNumber == 0) {
        largestNumber = ab - 1;
        maxBaseBlength -= 1;
      }
      largestNumber = largestNumber.toString();
      if (largestNumber.length < abString.length) {
        var difference = abString.length - largestNumber.length;
        var temporaryOne = "";
        for (var i = 0; i < difference; i++) {
          temporaryOne += "0";
        }
        temporaryOne += largestNumber;
        largestNumber = temporaryOne;
      }
      for (var i = 0; i < maxBaseBlength; i++) {
        temporary += largestNumber;
      }

      maxSameNumbers = temporary;
    }

    var maxSameNumbersArray = [];
    var currentNumber = "";

    for (var i = 0; i < maxSameNumbers.length; i++) {
      currentNumber += maxSameNumbers.charAt(i);
      if ((i + 1) % abString.length == 0) {
        maxSameNumbersArray.push(currentNumber);
        currentNumber = "";
      }
    }

    var j = 0;
    maxSameNumbers = 0;
    // Converts the number we found into decimal (base 10)
    for (var i = maxSameNumbersArray.length - 1; i >= 0; i--) {
      var currentNum = parseInt(maxSameNumbersArray[i]);
      maxSameNumbers += currentNum * Math.pow(ab, j);
      j++;
    }

    document.getElementById("resultA").innerHTML = "result: " + maxSameNumbers;
  } else if (an <= ab) {
    document.getElementById("resultA").innerHTML =
      "result: Not Found, Max Allowed has to be larger than base";
  } else {
    document.getElementById("resultA").innerHTML = "result: Invalid Input";
  }
}

/*
    Retrieves partB inputs from user web interface. Will find an integer value
    that when converted to both bases (the two user inputs), will have repeated 
    digits in both bases.
    */
function partB() {
  var bb = parseInt(document.getElementById("bb").value);
  var bc = parseInt(document.getElementById("bc").value);

  // Checks whether input values are valid.
  if (bb > 1 && bc > 1) {
    var currentInt = 0;
    if (bb > bc) {
      currentInt = bb + 1;
    } else {
      currentInt = bc + 1;
    }

    while (true) {
      var bbNum = baseConvert(currentInt, bb);
      var bcNum = baseConvert(currentInt, bc);

      if (repeatedNumCheck(bbNum)) {
        if (repeatedNumCheck(bcNum)) {
          break;
        }
      }
      currentInt++;
    }
    document.getElementById("resultB").innerHTML = "result: " + currentInt;
  } else {
    document.getElementById("resultB").innerHTML = "result: Invalid Input";
  }
}
