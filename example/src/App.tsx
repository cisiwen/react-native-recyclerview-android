import * as React from 'react';
import { NativeEventEmitter, NativeModules } from 'react-native';

import { StyleSheet, View } from 'react-native';
import {
  RecyclerviewAndroidView,
  SectionDataSource,
} from 'react-native-recyclerview-android';

export default function App() {
  let now = new Date().getTime().toString();
  let sections: SectionDataSource[] = [];
  React.useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModules.RecyclerviewAndroidView);
   const eventListener = eventEmitter.addListener('ON_ITEM_LONG_PRESS', (event: { eventProperty: any; }) => {
      console.log(event.eventProperty) // "someValue"
   });
   return () => {
     eventListener.remove();
   }
  }, []);
  for (let s = 0; s < 50; s++) {
    let section: SectionDataSource = {
      sectionTitle: `Gallery section ${s}`,
      sectionId: s.toString(),
      data: [],
    };
    for (let i = 0; i < 120; i++) {
      section.data.push({
        contentId: `${i}`,
        uri: 'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        contentUri: null, //'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        width: now,
        title: null,
        mediaType: 'image/jpeg',
        height: now,
      }); 
    }
    sections.push(section);
  }

  console.log(now, new Date().getTime().toString());
  now = new Date().getTime().toString();
  const dataSourceString = JSON.stringify(sections);
  console.log('json.stringfy', now, new Date().getTime().toString());
  return (
    <View style={styles.container}>
      <RecyclerviewAndroidView
        dataSourceString={dataSourceString}
        color="#32a852"
        style={styles.box}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: '100%',
    height: '100%',
    backgroundColor: 'green',
    margin: 0,
  },
});
