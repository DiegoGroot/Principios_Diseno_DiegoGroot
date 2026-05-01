class Singleton{
static final Singleton _singleton = Singleton.internal();

  factory Singleton() {
    return _singleton;
  }
  
  Singleton.internal();
}

main() {
  var s1 = Singleton();
  var s2 = Singleton();
  print(identical(s1, s2));  // true
  print(s1 == s2);           // true
}

