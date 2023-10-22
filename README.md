# pict-converter

油絵風に画像変換処理をしてくれるwebサイトです。

## 実装方法

### 1. Springbootフォルダのダウンロード
-  `Springboot` フォルダをダウンロードします。

### 2. cycleganのダウンロード
- (https://github.com/soda143/cyclegan) から `cyclegan` をダウンロードします。
  - **注**: `cyclegan` の実装方法は上記のURLで確認してください。
  - 学習済みモデル「`oil_painting_weights.h5`」を使用して油絵風に画像を変換します。

### 3. パスの設定
ダウンロードした `Springboot` フォルダ内の以下のファイルを編集して、指定されたパスを追加します。

- **PictConvConst.java**: `Springboot\src\main\java\com\samurai\pictconverter\PictConvConst.java`
  ```java
  public static final String beforeDir = "cyclegan内、「original_image」フォルダまでの絶対パス";  // アップロードされた画像が保存されるパス
  public static final String afterDir = "cyclegan内、「result」フォルダまでの絶対パス";  // pythonで変換した画像が保存されるパス
　
- **PictConvTopServiceImpl.java**: `Springboot\src\main\java\com\samurai\pictconverter\service\PictConvTopServiceImpl.java`

```java
 String scriptPath = "C:\\public static final String beforeDirと同じパス...";
 String dataRoot = "C:\\cycleganの実行ファイルまでのパス...\\testA";
 String modelName = "C:\\学習済みモデルまでのパス\\style_vangogh_pretrained";
```

### 4. アプリケーションの実行
- `Springboot\src\main\java\com\samurai\pictconverter\PictConverterApplication.java` を実行します。

### 5. Webアクセス
- ブラウザを起動して、[http://localhost:8080/top](http://localhost:8080/top) にアクセスします。
