import * as React from 'react';

import { PermissionsAndroid, Platform, StyleSheet, UIManager, View } from 'react-native';
import {
  PagerViewOnPageSelectedEvent,
  RecyclerviewAndroidView,
  SectionDataSource,
  SectionHeaderStyle,
  Commands,
} from '@cisiwen/react-native-recyclerview-android';
import type {
  MediaHttpHeaders,
  RecyclerProps,
} from '@cisiwen/react-native-recyclerview-android';

export default function App() {
  let now = new Date().getTime().toString();
  let sections: SectionDataSource[] = [];

  const hasAndroidPermission = async () => {
    const getCheckPermissionPromise = () => {
      if (Platform.OS == "android" && Platform.Version >= 33) {
        return Promise.all([
          PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.READ_MEDIA_IMAGES),
          PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.READ_MEDIA_VIDEO),
        ]).then(
          ([hasReadMediaImagesPermission, hasReadMediaVideoPermission]) =>
            hasReadMediaImagesPermission && hasReadMediaVideoPermission,
        );
      } else {
        return PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE);
      }
    };

    const hasPermission = await getCheckPermissionPromise();
    if (hasPermission) {
      return true;
    }
    const getRequestPermissionPromise = () => {
      if (Platform.OS == "android" && Platform.Version >= 33) {
        return PermissionsAndroid.requestMultiple([
          PermissionsAndroid.PERMISSIONS.READ_MEDIA_IMAGES,
          PermissionsAndroid.PERMISSIONS.READ_MEDIA_VIDEO,
        ]).then(
          (statuses) =>
            statuses[PermissionsAndroid.PERMISSIONS.READ_MEDIA_IMAGES] ===
            PermissionsAndroid.RESULTS.GRANTED &&
            statuses[PermissionsAndroid.PERMISSIONS.READ_MEDIA_VIDEO] ===
            PermissionsAndroid.RESULTS.GRANTED,
        );
      } else {
        return PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE).then((status) => status === PermissionsAndroid.RESULTS.GRANTED);
      }
    };

    return await getRequestPermissionPromise();
  }

  React.useEffect(() => {
    (
      async () => {
        if (Platform.OS == "android") {
          let has = await hasAndroidPermission();
          console.log('hasAndroidPermission', has);
        }
      }
    )()
  }, ["text"])
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
  for (let s = 0; s < 200; s++) {
    let section: SectionDataSource = {
      sectionTitle: `Gallery section ${s}`,
      sectionId: s.toString(),
      data: [],
    };
    for (let i = 0; i < 50; i++) {
      section.data.push({
        contentId: `${i}`,
        uri:
          i == -1
            ? 'ph://CC95F08C-88C3-4012-9D6D-64A413D254B3/L0/001/IMG_0111.HEIC'
            : 'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        contentUri: null, //'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        width: now,
        title: null,
        mediaType: parseInt((Math.random() * 100).toString()) % i > 0 ? 'video' : 'photo',
        height: now,
      });
    }
    sections.push(section);
  }

  const refreshNewData = () => {
    let updateSections: SectionDataSource[] = [];
    let section: SectionDataSource = {
      sectionTitle: `Gallery section ${sections.length}`,
      sectionId: sections.length.toString(),
      data: [],
    };
    for (let i = 0; i < 50; i++) {
      section.data.push({
        contentId: `${i}`,
        uri:'https://4.img-dpreview.com/files/p/TS1200x900~sample_galleries/1330372094/7004100121.jpg',
        contentUri: null, //'https://live.staticflickr.com/3469/3700376791_c5833828b3_b.jpg',
        width: now,
        title: null,
        mediaType: parseInt((Math.random() * 100).toString()) % i > 0 ? 'video' : 'photo',
        height: now,
      });
    }
    updateSections.push(section);
    console.log('refreshNewData', sections.length);
    Commands.updateDataSource(recyclerview, JSON.stringify(updateSections));
  }

  const onLongPressed = (e: PagerViewOnPageSelectedEvent) => {
    console.log('onLongPressed', e.nativeEvent);
  };

  const onRNSelectionModeChanged = () => {
    Commands.toggleSelectionMode(recyclerview, true);
  };
  const onItemPressed = (e: PagerViewOnPageSelectedEvent) => {
    console.log('onItemPressed', e.nativeEvent);
    onRNSelectionModeChanged();
  };
  const onItemSelectStateChanged = (e: PagerViewOnPageSelectedEvent) => {
    console.log('onItemSelectStateChanged', e.nativeEvent);
  };
  console.log(now, new Date().getTime().toString());
  now = new Date().getTime().toString();
  const dataSourceString = JSON.stringify(sections);
  console.log('json.stringfy', now, new Date().getTime().toString());
  const sectionHeaderStyle: SectionHeaderStyle = {
    BackgroudColor: '#000000',
    FontSize: 20,
    FontWeight: 600,
    FontColor: '#ffffff',
    Padding: 10,
  };

  let httpHeaders: MediaHttpHeaders = {
    cookie: 'testing',
  };

  let recyclerProps: RecyclerProps = {
    headerStyle: sectionHeaderStyle,
    httpHeaders: httpHeaders,
    data: sections,
  };
  let recyclerview: React.ElementRef<typeof RecyclerviewAndroidView> | null =
    null;
  return (
    <View style={styles.container}>
      {5 > 3 ? (
        <RecyclerviewAndroidView
          onLayout={(event: any) => {
            console.log('onLayout', event.nativeEvent);
          }}
          ref={(ref: React.ElementRef<typeof RecyclerviewAndroidView>) => {
            recyclerview = ref;
          }}
          OnRefreshing={(data:any) => {
            console.log('OnRefreshing', data.nativeEvent);
            refreshNewData();
          }}
          onLongPressed={onLongPressed}
          onItemPressed={onItemPressed}
          OnItemSelectStateChanged={onItemSelectStateChanged}
          onScrollBeginDrag={(event: any) => {
            console.log('onScrollBeginDrag', event.nativeEvent);
          }}
          onScroll={(event: any) => {
            console.log('onScroll', event.nativeEvent);
          }}
          onScrollEndDrag={(event: any) => {
            console.log('onScrollEndDrag', event.nativeEvent);
          }}
          onContentSizeChange={(event: any) => {
            console.log('onContentSizeChange', event.nativeEvent);
          }}
          onVisibleItemsChange={(event: any) => {
            console.log('onVisibleItemsChange', event.nativeEvent);
          }}
          recyclerPropString={JSON.stringify(recyclerProps)}
          color="#32a852"
          style={styles.box}
        />
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#ffcc00',
  },
  box: {
    flex: 1,
    overflow: 'hidden',
    backgroundColor: '#000',
    margin: 0,
  },
});
