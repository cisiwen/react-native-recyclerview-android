import * as React from 'react';

import { StyleSheet, View } from 'react-native';
import {
  Asset,
  RecyclerviewAndroidView,
} from 'react-native-recyclerview-android';

export default function App() {
  const dataSource: Asset[] = [];
  let now = new Date().getTime().toString();
  for (let i = 0; i < 10000; i++) {
    dataSource.push({
      contentId: `${i}`,
      uri: 'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
      contentUri: 'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
      width: now,
      mediaType: 'image/jpeg',
      height: now,
    });
  }
  console.log(now, new Date().getTime().toString());
  now = new Date().getTime().toString();
  const dataSourceString = JSON.stringify(dataSource);
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
