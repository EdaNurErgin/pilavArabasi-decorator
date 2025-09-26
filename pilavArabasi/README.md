## Pilav Arabası (Swing) – Dekoratör Deseni Örneği

Java Swing ile yazılmış, pilav siparişi oluşturmayı simüle eden bir masaüstü uygulaması. Kullanıcı, tabaktaki malzemeleri (Tavuk, Nohut, Turşu, Kavurma) işaretleyerek anlık açıklama ve toplam fiyatı görür. Uygulama, malzemeleri esnek biçimde ekleyip çıkarmak için Dekoratör (Decorator) tasarım desenini kullanır.

### Özellikler
- Modern görünümlü Swing arayüzü
- Anlık fiyat ve açıklama güncellemesi
- Sade Pilav üzerine malzemeleri Dekoratör deseniyle katmanlı ekleme
- Görsellerle zenginleştirilmiş bileşenler (`src/images`)

### Proje Yapısı
```
src/
  Main.java                 // Swing arayüzü ve akış
  Pilav.java                // Bileşen arayüzü
  SadePilav.java           // Somut bileşen
  PilavDecorator.java      // Dekoratör soyut sınıfı
  TavukDecorator.java      // Somut dekoratörler
  NohutDecorator.java
  TursuDecorator.java
  KavurmaDecorator.java
  images/                  // Arayüzde kullanılan görseller
```

### Nasıl Çalışır? (Dekoratör Deseni)
- `Pilav` arayüzü, tüm pilav türlerinin sağlaması gereken `getDescription()` ve `getCost()` davranışlarını tanımlar.
- `SadePilav`, temel üründür.
- `PilavDecorator`, bir `Pilav` referansı tutar ve davranışı delegasyonla genişletir.
- `TavukDecorator`, `NohutDecorator`, `TursuDecorator`, `KavurmaDecorator` maliyet ve açıklamayı katmanlı olarak artırır.
- `Main`, seçilen kutulara göre dekoratörleri zincirleyerek nihai ürünü oluşturur ve özet bölmesinde gösterir.

### Gereksinimler
- Java 8+ (JDK)
- (Tercihen) IntelliJ IDEA veya başka bir Java IDE

### Çalıştırma

#### 1) IntelliJ IDEA ile (Önerilen)
1. Projeyi IDEA ile açın (`pilavArabasi.iml` otomatik algılanır).
2. `src` kaynak kökü, `src/images` ise kaynaklar (resources) olarak işaretli olmalıdır.
3. `Main` sınıfını çalıştırın.

İpucu: IDEA, derlenen sınıfları ve görselleri otomatik olarak `out/production/pilavArabasi` altına kopyalar.

#### 2) PowerShell ile komut satırından (Windows)
Proje kökünde aşağıdaki adımları uygulayın:

```powershell
# Derle (out klasörüne)
New-Item -ItemType Directory -Force out | Out-Null
javac -d out src/*.java

# Görselleri çalışma zamanında bulunabilir yapmak için kopyala
New-Item -ItemType Directory -Force out/images | Out-Null
Copy-Item -Recurse -Force src/images/* out/images/

# Çalıştır
java -cp out Main
```

Alternatif: Bir jar dosyası üretip `images` klasörünü jar dışında aynı dizine kopyalayarak da çalıştırabilirsiniz.

### Sık Karşılaşılan Sorunlar
- Görseller yüklenmiyor: `Main#loadScaled` kaynakları sınıf yolundan `/images/...` olarak arar. Çalıştırırken `images` klasörünün sınıf yolunun kökünde olduğundan emin olun (örneğin `out/images`).
- Türkçe karakter ve fontlar: Sistem yazı tipleri kullanılır; işletim sisteminizin Türkçe yerel ayarlarıyla uyumludur.

### Lisans
Bu proje eğitim amaçlı bir örnektir. Dilediğiniz gibi inceleyip uyarlayabilirsiniz.


