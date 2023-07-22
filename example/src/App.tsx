import * as React from 'react';

import { StyleSheet, UIManager, View } from 'react-native';
import {
  PagerViewOnPageSelectedEvent,
  RecyclerviewAndroidView,
  SectionDataSource,
  SectionHeaderStyle,
  Commands
} from '@cisiwen/react-native-recyclerview-android';
 

 

export default function App() {
  let now = new Date().getTime().toString();
  let sections: SectionDataSource[] = [];
  /*
  React.useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModules.RecyclerviewAndroidView);
   const eventListener = eventEmitter.addListener('ON_ITEM_LONG_PRESS', (event: { eventProperty: any; }) => {
      console.log(event.eventProperty) // "someValue"
   });
   return () => {
     eventListener.remove();
   }
  }, []);*/
  for (let s = 0; s < 50; s++) {
    let section: SectionDataSource = {
      sectionTitle: `Gallery section ${s}`,
      sectionId: s.toString(),
      data: [],
    };
    for (let i = 0; i < 100; i++) {
      section.data.push({
        contentId: `${i}`,
        uri:
          i == -1
            ? 'ph://CC95F08C-88C3-4012-9D6D-64A413D254B3/L0/001/IMG_0111.HEIC'
            : 'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        contentUri: null, //'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        width: now,
        title: null,
        mediaType: 'image/jpeg',
        height: now,
      });
    }
    sections.push(section);
  }

  const onLongPressed = (e: PagerViewOnPageSelectedEvent) => {
    console.log('onLongPressed', e.nativeEvent);
  };

  const onRNSelectionModeChanged = () => {
    Commands.toggleSelectionMode(recyclerview, true);
  };
  const onItemPressed=(e:PagerViewOnPageSelectedEvent)=>{
    console.log('onItemPressed', e.nativeEvent);
    onRNSelectionModeChanged();
  }
  const onItemSelectStateChanged=(e:PagerViewOnPageSelectedEvent)=>{
    console.log('onItemSelectStateChanged', e.nativeEvent);
  }
  console.log(now, new Date().getTime().toString());
  now = new Date().getTime().toString();
  const dataSourceString = JSON.stringify(sections);
  console.log('json.stringfy', now, new Date().getTime().toString());
  const sectionHeaderStyle:SectionHeaderStyle = {
    BackgroudColor: '#000000',
    FontSize: 20,
    FontWeight: 600,
    FontColor: '#ffffff',
    Padding: 10
  }
  
  let recyclerview:React.ElementRef<typeof RecyclerviewAndroidView>|null = null;
  return (
    <View style={styles.container}>
      
      { 7>3 ?
      <RecyclerviewAndroidView
        ref={(ref:React.ElementRef<RecyclerviewAndroidView>) => {
          recyclerview = ref;
        }}
        sectionHeaderStyle={JSON
          .stringify(sectionHeaderStyle)}
        onLongPressed={onLongPressed}
        onItemPressed={onItemPressed}
        OnItemSelectStateChanged={onItemSelectStateChanged}
        dataSourceString={dataSourceString}
        color="#32a852"
        style={styles.box}
      />:null
         }
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#ffcc00',
  },
  box: {
    flex:1,
    overflow: 'hidden',
    backgroundColor: 'green',
    margin: 0,
  },
});
