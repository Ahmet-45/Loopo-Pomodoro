# Loopo - Pomodoro Timer Masaüstü Uygulaması

## 📖 Proje Tanımı

Loopo, ders çalışma, iş yerinde odaklanma veya günlük görevler için Pomodoro tekniğini (25 dakika çalışma, 5 dakika mola) uygulayan, Java 23 ve JavaFX tabanlı kullanıcı dostu bir masaüstü zamanlayıcıdır.

## 🚀 Özellikler

* **Özelleştirilebilir süreler:** `work.duration` ve `break.duration` değerlerini `config.properties` ile değiştirebilirsiniz.
* **Tema Desteği:** Light veya Dark tema seçenekleri.
* **İstatistik Ekranı:** Günlük tamamlanan odak sayısı ve toplam odak süresini görüntüler.
* **Kullanıcı Yönetimi:** Basit kayıt / giriş ekranı.
* **SQLiteTabanlı Veri Kaydı:** Odak oturumları `loopo.db.sqbpro` veritabanında saklanır.

## ⚙️ Teknik Gereksinimler

* **Java:** 23
* **Build Aracı:** Maven
* **Arayüz:** JavaFX SDK
* **Veritabanı Sürücüsü:** sqlite-jdbc-3.49

## 📦 Kurulum

1. Repoyu klonlayın:

   ```bash
   git clone https://github.com/Ahmet-45/Loopo-Pomodoro.git
   cd Loopo-Pomodoro
   ```
2. Maven ile bağımlılıkları indirin:

   ```bash
   mvn clean install
   ```
3. IDE (Eclipse/IntelliJ IDEA) ile projeyi açın.
4. Gerekirse JavaFX SDK yolunu proje ayarlarında belirtin.

## ⚙️ Konfigürasyon (`config.properties`)

Proje kök dizininde `config.properties` dosyanız şöyle görünmelidir:

```properties
# Veritabanı dosya yolu
# Proje klasöründeki .sqbpro dosyasını gösterir
db.path=loopo.db.sqbpro

# Süre ayarları (dakika cinsinden)
work.duration=25
break.duration=5

# Tema: light veya dark
theme=dark
```

## ▶️ Kullanım

1. Maven ile proje derlendikten sonra IDE’de `MainApp.java` dosyasını çalıştırın.
2. İlk açılışta **Kayıt** ekranı gelir. Yeni kullanıcı oluşturarak kayıt olun.
3. Oluşturduğunuz kullanıcı adı ve şifre ile **Giriş** yapın.
4. Ana ekranda zamanlayıcıyı başlatmak için **Başlat** butonuna tıklayın.
5. Sol üst köşedeki menüden **Ayarlar** sekmesine girerek süreleri ve temayı özelleştirebilirsiniz.
6. Her tamamlanan çalışma/mola döngüsünde veri kaydedilir; **İstatistik** ekranından geçmiş oturumlarınızı görüntüleyin.

## 🤝 Katkıda Bulunma

1. Repo’yu fork’layın.
2. Yeni branch oluşturun: `git checkout -b feature/yeni-özellik`
3. Değişiklikleri commit edin: `git commit -m "Yeni özellik ekle"`
4. Branch’i push’layın: `git push origin feature/yeni-özellik`
5. Pull request açın.

## 📜 Lisans

Bu proje **MIT Lisansı** ile lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakabilirsiniz.

## 📬 İletişim

Sorularınız için: [cannngumus@gmail.com](mailto:cannngumus@gmail.com)
