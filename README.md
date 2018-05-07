# builder-annotation
An annotation processor which implements "Builder Pattern" for your java classes.

---

### Example:
we annotated our class with `@Builder` and initialize the constructor with all the fields.

```java
@Builder
public class User {
    private int id;
    private String name;
    private String pass;
    private String email;
    private String age;

    public User(int id, String name, String pass, String email, String age) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.age = age;
    }
}

```

---

### Result:
generated class, the compiler did the rest for us to avoid boilerplates and repeated codes.
```java
public class User_Builder {

  private int id;
  private String name;
  private String pass;
  private String email;
  private String age;

  public User_Builder() {
  }

  public User_Builder setId(int id) {
    this.id = id;
    return this;
  }

  public User_Builder setName(String name) {
    this.name = name;
    return this;
  }

  public User_Builder setPass(String pass) {
    this.pass = pass;
    return this;
  }

  public User_Builder setEmail(String email) {
    this.email = email;
    return this;
  }

  public User_Builder setAge(String age) {
    this.age = age;
    return this;
  }

  public User build() {
    return new User(id,name,pass,email,age);
  }
}
```
---

### Then:
```java
 User user = new User_Builder()
                ...
                .setName("Ali")
                .setEmail("ali@gmail.com")
                .build();
```
---

## Used libraries

- [Square's Javapoet](https://github.com/square/javapoet)
- [Google's AutoService](https://github.com/google/auto/tree/master/service)

## Useful reads

- [ANNOTATION PROCESSING 101 - by Hannes Dorfmann](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)
- [DroidconDE 2015: Annotation Processing 101 - by Hannes Dorfmann (VIDEO)](https://www.youtube.com/watch?v=43FFfTyDYEg)
- [Annotation Processing : Don’t Repeat Yourself, Generate Your Code](https://medium.com/@iammert/annotation-processing-dont-repeat-yourself-generate-your-code-8425e60c6657)
- [Annotation processing basics](https://medium.com/@hyperandroid/annotation-processing-basics-597093c2b2ac)
- [Annotation Processing for Android](https://medium.com/@robhor/annotation-processing-for-android-b7eda1a41051)

```
MIT License
Copyright (c) 2018 Ali Esa Assadi
```
