- Description:
  Demo application to show long form for a given acronym. We are using http://www.nactem.ac.uk/software/acromine/rest.html API, no api-key required.

- Design and architecture:
  App is desinged with single Activity approach in mind and navigation component, currently it has only one fragment, but it is easily scalable.<br/><br>
  
  <a href="https://ibb.co/1Q1T5qc"><img src="https://i.ibb.co/r0YHS69/Screen-Shot-2020-12-13-at-10-53-07-AM.png" alt="Screen-Shot-2020-12-13-at-10-53-07-AM" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'>upload images</a><br />
  
 - App uses following tech:<br/><br>
    Networking - Retrofit<br/><br>
    Networking - Coroutines<br/><br>
    Jetpack - Navigation<br/><br>
    DI - Hilt<br/><br>
    DataBinding<br/><br>
    JUnit - Testing<br/><br>
    
- How app is designed:<br/><br>
    Retrofit object in injected to Repository class which is in charge of making a network call. Repository and response object are injected to ViewModel so it       can setup LiveData variables based on response results for UI to observe. UI then decides which widgets to show. ViewModel is also injected into                 SearchFragment. Databinding is used for RecyclerView, but could not be used with search bar listeners due to complications from ViewModel injection.<br/><br>
    
    <a href="https://ibb.co/syQNL6L"><img src="https://i.ibb.co/HnYbmxm/Arch.jpg" alt="Arch" border="0"></a>
        
- Notes and thoughts:

- Testing:

  1- Used Charles Proxy for mac to simulate slow commenction, no problems observed. <br/><br>
  2- Tested across configuration changes, and sending app to background and bringing back to foreground
  to make sure it preserves state.  <br/><br>
  
<a href="https://ibb.co/PjPnXsX"><img src="https://i.ibb.co/NCJPDgD/Screen-Shot-2020-12-13-at-10-53-14-AM.png" alt="Screen-Shot-2020-12-13-at-10-53-14-AM" border="0"></a>
  
  <br/><br>
  Please see: https://youtu.be/Rcs0OzhEWig
  
- App ANR'ed only once during many dry runs, looking at the stack i believe this is due to pressure
from starting emulator on my mac while having other processes running.

- Wrote four unit tests, including:

  1- Make sure reponse analyzer class is able to detect unauthorized response.<br/><br>
  2- Make sure reponse analyzer class is able to detect empty body response.<br/><br>
  3- Make sure reponse analyzer class is able to detect null response.<br/><br>
  4- Make sure reponse analyzer class is able to detect OK response.<br/><br>
  
- Other thoughts and notes:

  App has a clean build with one warning on generated hilt files, probably outside of author's control.
  <br/><br>
    <a href="https://ibb.co/GWxdsJp"><img src="https://i.ibb.co/gSjrv79/Screen-Shot-2020-12-13-at-10-00-03-AM.png" alt="Screen-Shot-2020-12-13-at-10-00-03-AM" border="0"></a>

- Possible improvements:<br/><br>
  1- Use Room to cache results so we do not make a web call every time.<br/><br>
  2- Use expose annotation to only filter for what we actually need.<br/><br>
  3- Add timeout to Retrofit request.<br/><br>
  4- Use Generic Api Response from Google samples and wrap Retrofit response.
  
