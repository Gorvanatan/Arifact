#  PixelPeek
### by Gorvanatan

> A JavaFX desktop app that takes a single search word, fetches a photo from the Unsplash API, and reveals detailed metadata about it.

---

## What It Does

1. Type one word into the search bar (e.g. `ocean`, `city`, `forest`)
2. PixelPeek fetches a matching photo from Unsplash
3. The photo fades onto the screen
4. After 2 seconds, a details panel appears showing:
   - 📐 Image dimensions (width × height in pixels)
   - 🎨 Dominant colour (hex code + colour swatch)
   - 🖼️ File format
   - 📝 Photo description
   - 📸 Photographer name
   - 🔗 Source URL (clickable — opens in your browser)
5. After 5 seconds, a **Search Again** button slides up from the bottom *(it's invisible until then, but it's already clickable if you know where it is!)*

---

## How It Works

PixelPeek sends the user's search query to the Unsplash REST API, which returns a JSON response containing the photo URL and metadata. The app parses that JSON into a `PhotoData` object and displays it — a different random result each time, even for the same query.

---

## Tech Used

| Tech | Purpose |
| --- | --- |
| Java 17 | Core language |
| JavaFX 21 | UI framework (scenes, animations, layout) |
| Unsplash API | Photo search + metadata |
| Java HttpClient | HTTP requests (built into Java 11+) |
| Gson 2.10 | JSON parsing of API responses |
| Maven | Build tool and dependency management |

---

## Setup Instructions

> Anyone can run this, but you'll need a couple of things first.

### What you need

- Java 17 or higher — [download](https://adoptium.net/)
- Maven — [download](https://maven.apache.org/)
- A free Unsplash Developer account

### Step 1 — Get a free Unsplash API key

1. Go to [unsplash.com/developers](https://unsplash.com/developers)
2. Click **Your apps → New Application**
3. Accept the terms and fill in the form
4. Copy your **Access Key**

### Step 2 — Add your API key

Open `src/main/java/com/pixelpeek/UnsplashService.java` and replace:

```java
private static final String ACCESS_KEY = "YOUR_UNSPLASH_ACCESS_KEY";
```

with your actual key.

### Step 3 — Clone and run

```bash
git clone https://github.com/Gorvanatan/Arifact.git
cd Arifact
mvn javafx:run
```

That's it — the app window will open.

---

## Screenshots

### Screen 1 — Search

Type any single word into the search bar and press Enter.

<img width="847" height="921" alt="Search screen" src="https://github.com/user-attachments/assets/e1a72d7b-4b1f-47d0-acb1-dc882d84196a" />

### Screen 2 — Result

The photo appears, then the details panel fades in. After 5 seconds the **Search Again** button slides up. The screen is fully scrollable.

<img width="850" height="907" alt="Result screen" src="https://github.com/user-attachments/assets/46523f5b-b3a3-441d-b92e-adda5638997f" />
<img width="827" height="832" alt="Result screen scrolled" src="https://github.com/user-attachments/assets/29b4d45d-112e-4fdc-8d00-d0c9eb789aa5" />


---

## AI Acknowledgment

This project was built with assistance from Claude (Anthropic) — used to help scaffold the project structure, suggest JavaFX animation approaches (`FadeTransition`, `TranslateTransition`), debug the module system configuration in `module-info.java`, and review this README.

All code was reviewed, tested, and understood before being included. The core logic — API integration, scene transitions, and UI structure — was implemented and validated by the developer.

---

## License

MIT — free to use and modify.
