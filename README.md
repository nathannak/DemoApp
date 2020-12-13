Description:
* Demo application to show long form for a given acronym. We are using http://www.nactem.ac.uk/software/acromine/rest.html API, no api-key required.

Design and architecture:
App uses following tech:
DI - Hilt


Notes and thoughts:



Testing:

* Used Charles Proxy for mac to simulate slow commenction, no problems observed.

* App ANR'ed only once during many tests, looking at the stack i believe this is due to pressure
from starting emulator on my mac.

* Used four unit tests, inclusing:
  1- 
  2- 
  3- 
  4- 

* Possible Improvements:
  1- Use Room to cache results so we do not make a web call every time.
  2- Use expose annotation to only filter for what we actually need.
  3- Add timeout to Retrofit request




