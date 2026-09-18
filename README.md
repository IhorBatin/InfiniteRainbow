# Infinite Rainbow

A color palette generator and design tool for Android — explore infinite unique colors, build full palettes, and export share-ready images for your next project.

## 🎨 Features

- **Infinite Discovery**: Explore a never-ending stream of unique, randomly generated colors.
- **Guaranteed Uniqueness**: Deduplication logic ensures every color you discover is genuinely new.
- **Palette Explorer**: Generate full design palettes from any color — Monochromatic, Analogous, and Complementary variations.
- **Spectrum Walking**: Tap any color within a palette to instantly re-center the explorer on that hue and keep exploring from there.
- **Favorites Collection**: Save colors you like to a persistent gallery, powered by Jetpack DataStore.
- **Professional Sharing**: Export high-resolution, social-media-ready images of your custom palettes — ready to share or drop into a design file.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (100%)
- **Navigation**: Compose Navigation
- **Storage**: Jetpack DataStore (Preferences)
- **Architecture**: Clean Architecture
- **Target SDK**: 37 (Android 15+)

## 📸 Screenshots

| | |
|---|---|
| <img width="427" height="952" alt="color_l" src="https://github.com/user-attachments/assets/6376bc5c-674c-49b7-af50-95f4a8e12db9" /> | <img width="427" height="952" alt="color_p" src="https://github.com/user-attachments/assets/3fe92061-2d19-466f-9155-4226a33db15e" /> |

## 📦 Project Setup

To get started:

1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle and run the `:app` module.


## 🤖 Development Notes

This project doubled as an experiment in how far AI-assisted tooling (Gemini) could go — scaffolding, iteration, and a good chunk of implementation were AI-driven, with me steering architecture decisions (Clean Architecture structure, palette-generation logic, DataStore integration) and reviewing/adjusting the output throughout.

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

*A side project built for exploring color theory and giving designers/creatives a fast way to generate and export palettes.*
