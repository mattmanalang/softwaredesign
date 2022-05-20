# Output Writers

<p> The goal of this assignment was to create two kinds of Writers: <br>
  <strong>StringWriter</strong>(which writes to a String, not related to already built-in classes in Java/C#, etc.) and <strong>FileWriter</strong>(which writes to a file, again not related to the classes provided in Java/C#, etc.)
  <br>There can be other types of Writers (like SocketWriter that writes to a Socket) in the future. </p>
  
<p> A Writer simply writes to a target until a close is called. Any effort to write after the call to close is simply ignored.<br>
You can find out what was written to a StringWriter by calling a function. You can also find out what was written to a file using a FileWriter by reading the file that was written to.</p>

<p> There are several types of operations that can be performed on these two (or other types added in the future) Writers. For example:<br>
  <ul>
    <li><strong>Lower case</strong>: This converts the string being written to all lower case.<br>
    <li><strong>Upper case</strong>: This converts the string being written to all upper case.<br>
    <li><strong>Stupid remover</strong>: This replaces the word "stupid" (only in lower case) to "s*****".<br>
    <li><strong>Duplicate remover</strong>: This removes consecutive duplicated words. For example, "This is is it" will be replaced by "This is it" when this function is applied.</p>
  </ul>
</p>

<p>Design so that other such functions may be added in the future, but without having to change any existing classes.<br>
  The user of your design will pick and choose what kinds of operations they want to use or combine to use.<br>
  For example, the stupid remover and duplicate remover may be combined to write to a file. The file woul then contain the content:
  <p>This is really s*****!!!</p>
</p>
  
