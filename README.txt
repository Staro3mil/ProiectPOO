Matache David-Nicolae
321CD

Primul obstacol in acest proiect a fost gasisrea unei metode de a evita folosirea excesiva de if-else pentru a verifica pe ce pagina sunt si ce actiuni pot face de acolo.
In acest scop am folosit Visitor patten, un HashMap si o interfata Page.
Interfata page contine 3 metode: onPage changePage si error.
Fiecare pagina de pe site foloseste aceste metode si implementeaza aceasta interfata. Am creeat o clasa pentru fiecare pagina in pare (Login, Register, Movies etc).
HashMap l-am creeat cu perechi de String-uri si obiecte de tip Page. Astfel la fiecare actiune verific pe ce pagina sunt dupa care ma duc in HashMap si preiau obiectul asociat String-ului cu numele paginii curente.
O data ce am obiectul specific paginii pe care ma aflu pot sa verific daca in input trebuie sa schimb pagina sau sa efectuez o actiune in cadrul acelei pagini. Astfel doar apelez metoda changePage sau onPage in functie de input.
Singura exceptie este atunci cand sunt pe pagina movies si vreau sa schimb pe see details, atunci folosesc metoda onPage deoarece acesta primeste ca argument actiunea curenta. Astfel pot sa aflu ce film vreau sa vad in sectiunea See Details.

Pentru variabile precum currentUser, currentMovies, currentPage etc. am realizat ca le voi folosi in cadrul intregului proiect si ca pasarea lor ca argumente in fiecare metoda ar fi consumat mult timp asa ca am ales sa fac o clasa speciala numita Globals.
In cadrul acestei clase am creeat proprietatile mentionate mai sus si le-am facut public si static pentru a putea fi apleate din orice clasa. 
In acest fel pot, de exemplu, sa schimb pagina curenta fara a prelua un argument in functia changePage pentru el.

Majoritatea metodelor si claselor sunt explicate prin comentarii insa clasa MovieComparator merita explicata in mai mare detaliu.
Am creeat aceasta clasa in scopul de a putea compara si sorta filmele atunci cand primesc inputul de sort. Avand in vedere ca trebuie sa sortez dupa doua proprietati nu puteam sa apelez o simpla metoda de sort.
Astfel am creeat MovieComparator care implementeaza Comparator si suprascrie metoda de compare intre doua obiecte de tip Movie. De altfel aceasta clasa are doua proprietati numite "duration" si "rating" care determina in ce ordine trebuie ordonate filmele sau daca nu trebuie ordonate decat in functie de o singura proprietate.
Am implementat doua metode simple numite compareInt si compareDouble pentru a compara proprietatile duration respectiv rating. 


