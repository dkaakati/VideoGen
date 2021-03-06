package videogen;

import com.google.common.base.Objects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.xtext.example.mydsl.VideoGenStandaloneSetupGenerated;
import org.xtext.example.mydsl.videoGen.AlternativeVideoSeq;
import org.xtext.example.mydsl.videoGen.MandatoryVideoSeq;
import org.xtext.example.mydsl.videoGen.OptionalVideoSeq;
import org.xtext.example.mydsl.videoGen.VideoDescription;
import org.xtext.example.mydsl.videoGen.VideoGeneratorModel;
import org.xtext.example.mydsl.videoGen.VideoSeq;
import videoGenQ2.MediaFile;
import videoGenQ2.Playlist;
import videoGenQ2.impl.VideoGenQ2FactoryImpl;

@SuppressWarnings("all")
public class projetInsertext {
  public VideoGeneratorModel loadVideoGenerator(final URI uri) {
    VideoGeneratorModel _xblockexpression = null;
    {
      VideoGenStandaloneSetupGenerated _videoGenStandaloneSetupGenerated = new VideoGenStandaloneSetupGenerated();
      _videoGenStandaloneSetupGenerated.createInjectorAndDoEMFRegistration();
      ResourceSetImpl _resourceSetImpl = new ResourceSetImpl();
      Resource res = _resourceSetImpl.getResource(uri, true);
      EList<EObject> _contents = res.getContents();
      EObject _get = _contents.get(0);
      _xblockexpression = ((VideoGeneratorModel) _get);
    }
    return _xblockexpression;
  }
  
  public void saveVideoGenerator(final URI uri, final VideoGeneratorModel pollS) {
    try {
      ResourceSetImpl _resourceSetImpl = new ResourceSetImpl();
      Resource rs = _resourceSetImpl.createResource(uri);
      EList<EObject> _contents = rs.getContents();
      _contents.add(pollS);
      HashMap<Object, Object> _hashMap = new HashMap<Object, Object>();
      rs.save(_hashMap);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static double getDuration(final String videoLocation) {
    try {
      Runtime _runtime = Runtime.getRuntime();
      Process process = _runtime.exec(("ffprobe -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 " + videoLocation));
      process.waitFor();
      InputStream _inputStream = process.getInputStream();
      InputStreamReader _inputStreamReader = new InputStreamReader(_inputStream);
      BufferedReader reader = new BufferedReader(_inputStreamReader);
      String line = "";
      String outputJson = "";
      while ((!Objects.equal((line = reader.readLine()), null))) {
        outputJson = (outputJson + line);
      }
      double _parseDouble = Double.parseDouble(outputJson);
      long _round = Math.round(_parseDouble);
      return (_round - 1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static String insertText(final String videoLocation, final String text) {
    try {
      System.out.println("debut");
      String[] name = videoLocation.split("\\.(?=[^\\.]+$)");
      String _get = name[0];
      String _plus = ((((("ffmpeg -i " + videoLocation) + " -vf drawtext=\'fontsize=15:fontfile=FreeSerif.ttf:text=") + text) + ":y=100:x=100\' -codec:a copy ") + _get);
      String cmd = (_plus + "-2.mp4");
      System.out.println(cmd);
      Runtime _runtime = Runtime.getRuntime();
      Process process = _runtime.exec(cmd);
      process.waitFor();
      InputStream _inputStream = process.getInputStream();
      InputStreamReader _inputStreamReader = new InputStreamReader(_inputStream);
      BufferedReader reader = new BufferedReader(_inputStreamReader);
      String line = "";
      String outputJson = "";
      while ((!Objects.equal((line = reader.readLine()), null))) {
        outputJson = (outputJson + line);
      }
      String _string = outputJson.toString();
      System.out.println(_string);
      String _get_1 = name[0];
      return (_get_1 + "-2.mp4");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void tp3_q7() {
    URI _createURI = URI.createURI("main.videogen");
    VideoGeneratorModel videoGen = this.loadVideoGenerator(_createURI);
    VideoGenQ2FactoryImpl fact = new VideoGenQ2FactoryImpl();
    Playlist playlist = fact.createPlaylist();
    Assert.assertNotNull(videoGen);
    EList<VideoSeq> _videoseqs = videoGen.getVideoseqs();
    Set<VideoSeq> _set = IterableExtensions.<VideoSeq>toSet(_videoseqs);
    for (final VideoSeq videoseq : _set) {
      if ((videoseq instanceof MandatoryVideoSeq)) {
        InputOutput.<String>println("Mandatory");
        VideoDescription _description = ((MandatoryVideoSeq) videoseq).getDescription();
        final String fileLocation = _description.getLocation();
        MediaFile mediafile = fact.createMediaFile();
        mediafile.setLocation(fileLocation);
        double _duration = projetInsertext.getDuration(fileLocation);
        mediafile.setDuration(_duration);
        mediafile.setInsertedText("Mandatory");
        String _location = mediafile.getLocation();
        String _insertedText = mediafile.getInsertedText();
        String locatTemp = projetInsertext.insertText(_location, _insertedText);
        mediafile.setLocation(locatTemp);
        String _location_1 = mediafile.getLocation();
        System.out.println(_location_1);
        EList<MediaFile> _mediafile = playlist.getMediafile();
        _mediafile.add(mediafile);
      } else {
        if ((videoseq instanceof OptionalVideoSeq)) {
          InputOutput.<String>println("Optional");
          Random _random = new Random();
          final int rand = _random.nextInt(2);
          if ((rand == 0)) {
            VideoDescription _description_1 = ((OptionalVideoSeq) videoseq).getDescription();
            final String fileLocation_1 = _description_1.getLocation();
            MediaFile mediafile_1 = fact.createMediaFile();
            mediafile_1.setLocation(fileLocation_1);
            double _duration_1 = projetInsertext.getDuration(fileLocation_1);
            mediafile_1.setDuration(_duration_1);
            mediafile_1.setInsertedText("Optional");
            String _location_2 = mediafile_1.getLocation();
            String _insertedText_1 = mediafile_1.getInsertedText();
            String locatTemp_1 = projetInsertext.insertText(_location_2, _insertedText_1);
            mediafile_1.setLocation(locatTemp_1);
            EList<MediaFile> _mediafile_1 = playlist.getMediafile();
            _mediafile_1.add(mediafile_1);
          }
        } else {
          InputOutput.<String>println("else");
          EList<VideoDescription> _videodescs = ((AlternativeVideoSeq) videoseq).getVideodescs();
          final int size = _videodescs.size();
          EList<VideoDescription> _videodescs_1 = ((AlternativeVideoSeq) videoseq).getVideodescs();
          Random _random_1 = new Random();
          int _nextInt = _random_1.nextInt(size);
          VideoDescription _get = _videodescs_1.get(_nextInt);
          String fileLocation_2 = _get.getLocation();
          MediaFile mediafile_2 = fact.createMediaFile();
          mediafile_2.setLocation(fileLocation_2);
          double _duration_2 = projetInsertext.getDuration(fileLocation_2);
          mediafile_2.setDuration(_duration_2);
          mediafile_2.setInsertedText("Alternative");
          String _location_3 = mediafile_2.getLocation();
          String _insertedText_2 = mediafile_2.getInsertedText();
          String locatTemp_2 = projetInsertext.insertText(_location_3, _insertedText_2);
          mediafile_2.setLocation(locatTemp_2);
          EList<MediaFile> _mediafile_2 = playlist.getMediafile();
          _mediafile_2.add(mediafile_2);
        }
      }
    }
    try {
      final File pl = new File("/home/dania/Documents/IDM/playlist-m3u-projet.m3u");
      boolean _exists = pl.exists();
      boolean _not = (!_exists);
      if (_not) {
        pl.createNewFile();
      }
      File _absoluteFile = pl.getAbsoluteFile();
      final FileWriter fw = new FileWriter(_absoluteFile);
      final BufferedWriter bw = new BufferedWriter(fw);
      String _lineSeparator = System.lineSeparator();
      String _plus = ("#EXTM3U" + _lineSeparator);
      bw.write(_plus);
      EList<MediaFile> _mediafile_3 = playlist.getMediafile();
      for (final MediaFile mediafile_3 : _mediafile_3) {
        double _duration_3 = mediafile_3.getDuration();
        String _plus_1 = ("#EXTINF:" + Double.valueOf(_duration_3));
        String _plus_2 = (_plus_1 + " ,Example Artist - Example title ");
        String _lineSeparator_1 = System.lineSeparator();
        String _plus_3 = (_plus_2 + _lineSeparator_1);
        String _location_4 = mediafile_3.getLocation();
        String _plus_4 = (_plus_3 + _location_4);
        String _lineSeparator_2 = System.lineSeparator();
        String _plus_5 = (_plus_4 + _lineSeparator_2);
        bw.write(_plus_5);
      }
      bw.close();
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private static int i = 0;
  
  private File ffmpeg;
  
  public static String genID() {
    int _plusPlus = projetInsertext.i++;
    return ("" + Integer.valueOf(_plusPlus));
  }
}
