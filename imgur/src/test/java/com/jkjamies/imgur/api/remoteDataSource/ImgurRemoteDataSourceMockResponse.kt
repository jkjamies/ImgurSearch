package com.jkjamies.imgur.api.remoteDataSource

internal val remoteDataSourceMockResponse =
    """
    {
        "data": [
            {
                "id": "whRQXAJ",
                "title": "Janky Vs The Guy She Told You Not to Worry About",
                "description": null,
                "datetime": 1716089945,
                "cover": "PUOfrQW",
                "cover_width": 1186,
                "cover_height": 938,
                "account_url": "skybike",
                "account_id": 74457,
                "privacy": "hidden",
                "layout": "blog",
                "views": 75,
                "link": "https://imgur.com/a/whRQXAJ",
                "ups": 6,
                "downs": 0,
                "points": 6,
                "score": 6,
                "is_album": true,
                "vote": null,
                "favorite": false,
                "nsfw": false,
                "section": "",
                "comment_count": 0,
                "favorite_count": 0,
                "topic": null,
                "topic_id": null,
                "images_count": 1,
                "in_gallery": true,
                "is_ad": false,
                "tags": [
                    {
                        "name": "derp",
                        "display_name": "derp",
                        "followers": 4705,
                        "total_items": 5574,
                        "following": false,
                        "is_whitelisted": false,
                        "background_hash": "OHK1wWY",
                        "thumbnail_hash": null,
                        "accent": "24565E",
                        "background_is_animated": false,
                        "thumbnail_is_animated": false,
                        "is_promoted": false,
                        "description": "",
                        "logo_hash": null,
                        "logo_destination_url": null,
                        "description_annotations": {}
                    },
                    {
                        "name": "funny",
                        "display_name": "Funny",
                        "followers": 6782898,
                        "total_items": 2566679,
                        "following": false,
                        "is_whitelisted": false,
                        "background_hash": "9r1qCDq",
                        "thumbnail_hash": null,
                        "accent": "633875",
                        "background_is_animated": false,
                        "thumbnail_is_animated": false,
                        "is_promoted": false,
                        "description": "LOLs, ROFLs, LMAOs",
                        "logo_hash": null,
                        "logo_destination_url": null,
                        "description_annotations": {}
                    },
                    {
                        "name": "cats",
                        "display_name": "Cats",
                        "followers": 214160,
                        "total_items": 140514,
                        "following": false,
                        "is_whitelisted": false,
                        "background_hash": "xeEIpAn",
                        "thumbnail_hash": null,
                        "accent": "BF63A7",
                        "background_is_animated": false,
                        "thumbnail_is_animated": false,
                        "is_promoted": false,
                        "description": "Our feline friends",
                        "logo_hash": null,
                        "logo_destination_url": null,
                        "description_annotations": {}
                    }
                ],
                "ad_type": 0,
                "ad_url": "",
                "in_most_viral": false,
                "include_album_ads": false,
                "images": [
                    {
                        "id": "PUOfrQW",
                        "title": "cute cat",
                        "description": null,
                        "datetime": 1716089899,
                        "type": "image/jpeg",
                        "animated": false,
                        "width": 1186,
                        "height": 938,
                        "size": 209638,
                        "views": 135,
                        "bandwidth": 28301130,
                        "vote": null,
                        "favorite": false,
                        "nsfw": null,
                        "section": null,
                        "account_url": null,
                        "account_id": null,
                        "is_ad": false,
                        "in_most_viral": false,
                        "has_sound": false,
                        "tags": [],
                        "ad_type": 0,
                        "ad_url": "",
                        "edited": "0",
                        "in_gallery": false,
                        "link": "https://i.imgur.com/PUOfrQW.jpg",
                        "comment_count": null,
                        "favorite_count": null,
                        "ups": null,
                        "downs": null,
                        "points": null,
                        "score": null,
                        "mp4_size": null,
                        "mp4": null,
                        "gifv": null,
                        "hls": null,
                        "processing": {
                            "status": "completed"
                        }
                    }
                ],
                "ad_config": {
                    "safeFlags": [
                        "album",
                        "in_gallery",
                        "gallery"
                    ],
                    "highRiskFlags": [],
                    "unsafeFlags": [
                        "updated_date"
                    ],
                    "wallUnsafeFlags": [],
                    "showsAds": false,
                    "showAdLevel": 1,
                    "safe_flags": [
                        "album",
                        "in_gallery",
                        "gallery"
                    ],
                    "high_risk_flags": [],
                    "unsafe_flags": [
                        "updated_date"
                    ],
                    "wall_unsafe_flags": [],
                    "show_ads": false,
                    "show_ad_level": 1,
                    "nsfw_score": 0
                }
            }
        ],
        "success": true,
        "status": 200
    }
    """.trimIndent()
