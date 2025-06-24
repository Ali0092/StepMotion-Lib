# 🚀 StepMotion

**StepMotion** is a lightweight, customizable, and elegant stepper UI library for Jetpack Compose. It helps you display multi-step progress in a clean and intuitive way — perfect for forms, onboarding flows, order tracking, and more.

---

## ✨ Features

- 🧽 Horizontal and Vertical Stepper with customizable steps and titles
- 🎨 Full color customization (active, inactive, title, indicator)
- ✅ Visually shows current, previous, and upcoming steps
- 🧹 Easy to integrate and use with clean APIs
- 💡 Ideal for progress tracking, sign-up forms, surveys, etc.

---

## 📸 Preview
<img src="https://github.com/user-attachments/assets/bf298f58-ab87-4fe3-9588-2d9857b779b3" width="40%">
<!-- ![stepMotion_demo](https://github.com/user-attachments/assets/bf298f58-ab87-4fe3-9588-2d9857b779b3) -->


---

## 🛠️ Installation

Coming soon to **MavenCentral** or **JitPack**\
For now, copy the code into your project and import the composables from:

```kotlin
import com.example.stepmotionlib.*
```

---

## 🛆 Usage

```kotlin
//Horizontal Stepper
HorizontalSimpleStepper(
    countingList = listOf(1, 2, 3, 4),
    titleList = listOf("Profile", "Upload", "Review", "Done"),
    selectedItemIndex = 2,
    nonSelectedItemColor = Color.LightGray,
    selectedItemColor = Color(0xFF3F51B5),
    nonSelectedTitleColor = Color.Gray,
    selectedTitleColor = Color.Black
)

//Vertical Stepper
VerticalSimpleStepper(
    countingList = listOf(1, 2, 3, 4),
    titleList = listOf("Profile", "Upload", "Review", "Done"),
    selectedItemIndex = 2,
    nonSelectedItemColor = Color.LightGray,
    selectedItemColor = Color(0xFF3F51B5),
    nonSelectedTitleColor = Color.Gray,
    selectedTitleColor = Color.Black
)


```

### Parameters

| Name                    | Type           | Description                                 |
| ----------------------- | -------------- | ------------------------------------------- |
| `countingList`          | `List<Int>`    | Step numbers shown in bubbles               |
| `titleList`             | `List<String>` | Titles under each step                      |
| `selectedItemIndex`     | `Int`          | Currently active step index (zero-based)    |
| `selectedItemColor`     | `Color`        | Color for selected and completed steps      |
| `nonSelectedItemColor`  | `Color`        | Color for upcoming/inactive steps           |
| `selectedTitleColor`    | `Color`        | Color for the current/completed step titles |
| `nonSelectedTitleColor` | `Color`        | Color for the inactive step titles          |

---


## ⚧️ Upcoming

- 🎮 Animated transitions
- 🧹 Modular customization slots (icons, progress bar styles)

---

## 🤝 Contribution

Feel free to fork, star, or suggest improvements via issues and pull requests.

---

## 📄 License

```
MIT License

Copyright (c) 2025 Muhammad Ali

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👤 Author

**Muhammad Ali**\
📧 [aliatwork364@gmail.com](mailto\:aliatwork364@gmail.com)\
🌐 [Portfolio](https://muhammadali0092.netlify.app)\
💼 [LinkedIn](https://www.linkedin.com/in/muhammad-ali-a28422222)\
💻 [GitHub](https://github.com/Ali0092)

---

> *“Step into motion — guide your users one smooth step at a time.”*

